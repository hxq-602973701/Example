package com.java1234.web.controller.task;

import com.github.pagehelper.PageInfo;
import com.java1234.dal.annotation.AuthCheck;
import com.java1234.dal.constant.page.SystemPageID;
import com.java1234.dal.constant.page.message.TaskMessage;
import com.java1234.dal.entity.main.schedulejob.ScheduleJob;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.utils.LoginContext;
import com.java1234.exception.DataErrorException;
import com.java1234.service.schedulejob.ScheduleJobService;
import com.java1234.util.Ids;
import com.java1234.util.ResponseCode;
import com.java1234.util.SpringUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 定时任务控制器
 *
 * @author lt 2018-8-1
 * @version 1.0.0
 */
@Controller
@RequestMapping("/task")
public class JobTaskController {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(JobTaskController.class);

    /**
     * 任务
     */
    @Resource
    private ScheduleJobService scheduleJobService;

    /**
     * 任务列表页面
     *
     * @return
     */
    @AuthCheck(page = SystemPageID.TASK_LIST)
    @RequestMapping(value = "task-list", method = RequestMethod.GET)
    public String taskListView() {

        return "system/task/task-list";
    }

    /**
     * 任务列表
     *
     * @param model
     * @return
     */
    @AuthCheck(page = SystemPageID.TASK_LIST)
    @RequestMapping(value = "task-list-api", method = RequestMethod.GET)
    public void taskListApi(final Model model, ScheduleJob param) {

        param.setDelFlag(false);
        PageInfo<ScheduleJob> taskList = scheduleJobService.selectPage(param);
        DataPipe.in(model).response(taskList);
    }

    /**
     * 任务添加页面
     *
     * @return
     */
    @AuthCheck(page = SystemPageID.TASK_LIST)
    @RequestMapping(value = "add-view", method = RequestMethod.GET)
    public String taskAddView(final Model model, ScheduleJob scheduleJob) {
        if (Ids.verifyId(scheduleJob.getJobId())) {
            model.addAttribute("scheduleJob", scheduleJobService.selectOne(scheduleJob));
        }
        return "system/task/task-edit";
    }

    /**
     * 添加新任务
     *
     * @param scheduleJob
     * @return
     */
    @AuthCheck(page = SystemPageID.TASK_LIST)
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public void taskList(ScheduleJob scheduleJob) {
        try {
            CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        } catch (Exception e) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, TaskMessage.TASK_CRON_ERROR);
        }
        Object obj = null;
        try {
            if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
                obj = SpringUtils.getBean(scheduleJob.getSpringId());
            } else {
                Class clazz = Class.forName(scheduleJob.getBeanClass());
                obj = clazz.newInstance();
            }
        } catch (Exception e) {

        }
        if (obj == null) {
            //目标类没找到
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, TaskMessage.TASK_TARGET_CLASS_NOT_FOUND);
        } else {
            Class clazz = obj.getClass();
            Method method = null;
            try {
                method = clazz.getMethod(scheduleJob.getMethodName(), null);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            if (method == null) {
                //目标方法没找到
                throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, TaskMessage.TASK_TARGET_METHOD_NOT_FOUND);
            }
        }
        try {
            scheduleJobService.addTask(scheduleJob);
        } catch (Exception e) {
            logger.error(e.getMessage());
            //name 或者group重复
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, TaskMessage.TASK_NAME_OR_GROUP_REPEAT);
        }
    }

    /**
     * 批量删除角色
     *
     * @param jobIds
     * @return
     */
    @AuthCheck(page = SystemPageID.TASK_LIST)
    @RequestMapping(value = "/system/task", method = RequestMethod.DELETE)
    public void deleteTasksApi(Long[] jobIds) {

        try {
            scheduleJobService.deleteJobs(jobIds);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        scheduleJobService.deleteWithLogicByPrimaryKeys(jobIds, LoginContext.getUserId(), "del_flag", "job_status");
    }

    /**
     * 开启停止任务
     *
     * @param jobId
     * @param cmd
     * @return
     * @throws SchedulerException
     */
    @AuthCheck(page = SystemPageID.TASK_LIST)
    @RequestMapping(value = "change-job-status", method = RequestMethod.POST)
    public void changeJobStatus(Long jobId, String cmd) throws SchedulerException {
        try {
            scheduleJobService.changeStatus(jobId, cmd);
        } catch (Exception e) {
            //任务状态改变失败
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, TaskMessage.TASK_STATUS_CHANGE_ERROR);
        }

    }

    /**
     * 更改时间表达式
     *
     * @param jobId
     * @param cron
     * @return
     * @throws SchedulerException
     */
    @AuthCheck(page = SystemPageID.TASK_LIST)
    @RequestMapping(value = "update-cron", method = RequestMethod.POST)
    public void updateCron(final Model model, Long jobId, String cron) throws SchedulerException {
        try {
            CronScheduleBuilder.cronSchedule(cron);
        } catch (Exception e) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, TaskMessage.TASK_CRON_ERROR);
        }
        scheduleJobService.updateCron(jobId, cron);
    }

}
