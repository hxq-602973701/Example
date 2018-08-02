package com.java1234.service.schedulejob.impl;

import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.schedulejob.ScheduleJobDAO;
import com.java1234.dal.entity.main.schedulejob.ScheduleJob;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.schedulejob.ScheduleJobService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.java1234.util.Ids;
import com.java1234.web.controller.task.QuartzJobFactory;
import com.java1234.web.controller.task.QuartzJobFactoryDisallowConcurrentExecution;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * ScheduleJobService
 *
 * @author lt 2018-8-2
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJob> implements ScheduleJobService {

    /**
     * ScheduleJobDAO
     */
    @Resource
    private ScheduleJobDAO scheduleJobDAO;

    /**
     * schedulerFactoryBean
     */
    @Resource
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<ScheduleJob> getDAO() {
        return scheduleJobDAO;
    }


    /**
     * 从数据库中取 区别于getAllJob
     *
     * @return
     */
    @Override
    public List<ScheduleJob> getAllTask() {
        return scheduleJobDAO.select(new ScheduleJob());
    }

    /**
     * 添加到数据库中 区别于addJob
     */
    @Override
    public void addTask(ScheduleJob job) throws SchedulerException {
        job.setCreateTime(new Date());
        if (Ids.verifyId(job.getJobId())) {
            //更新的话 直接停掉task
            ScheduleJob scheduleJob = scheduleJobDAO.selectByPrimaryKey(job.getJobId());
            if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
                deleteJob(scheduleJob);
            }
            scheduleJobDAO.updateByPrimaryKeySelective(job);
        } else {
            scheduleJobDAO.insertSelective(job);
        }
    }

    /**
     * 从数据库中查询job
     */
    @Override
    public ScheduleJob getTaskById(Long jobId) {
        return scheduleJobDAO.selectByPrimaryKey(jobId);
    }

    /**
     * 更改任务状态
     *
     * @throws SchedulerException
     */
    @Override
    public void changeStatus(Long jobId, String cmd) throws SchedulerException {
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        if ("stop".equals(cmd)) {
            deleteJob(job);
            job.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
        } else if ("start".equals(cmd)) {
            job.setJobStatus(ScheduleJob.STATUS_RUNNING);
            addJob(job);
        }
        scheduleJobDAO.updateByPrimaryKeySelective(job);
    }

    /**
     * 更改任务 cron表达式
     *
     * @throws SchedulerException
     */
    @Override
    public void updateCron(Long jobId, String cron) throws SchedulerException {
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        job.setCronExpression(cron);
        if (ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
            updateJobCron(job);
        }
        scheduleJobDAO.updateByPrimaryKeySelective(job);

    }

    /**
     * 添加任务
     *
     * @param
     * @throws SchedulerException
     */
    @Override
    public void addJob(ScheduleJob job) throws SchedulerException {
        if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
            return;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();

            jobDetail.getJobDataMap().put("scheduleJob", job);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 初始化
     *
     * @throws Exception
     */
    @PostConstruct
    @Override
    public void init() throws Exception {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        // 这里获取任务信息数据
        List<ScheduleJob> jobList = scheduleJobDAO.select(new ScheduleJob());

        for (ScheduleJob job : jobList) {
            addJob(job);
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    @Override
    public List<ScheduleJob> getAllJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJob job = new ScheduleJob();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    @Override
    public List<ScheduleJob> getRunningJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    @Override
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    @Override
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    @Override
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    @Override
    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    @Override
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 删除多个job
     *
     * @param jobIds
     * @return
     */
    @Override
    public void deleteJobs(Long[] jobIds) throws SchedulerException {


        for (Long jobId : jobIds) {
            //更新的话 直接停掉task
            ScheduleJob scheduleJob = scheduleJobDAO.selectByPrimaryKey(jobId);
            if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
                deleteJob(scheduleJob);
            }
        }
    }
}