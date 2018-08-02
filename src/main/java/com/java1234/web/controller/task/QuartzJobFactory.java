package com.java1234.web.controller.task;

import com.java1234.dal.entity.main.schedulejob.ScheduleJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author lt
 * @Description: 计划任务执行处理 无状态
 * @date 2018-08-24
 */
public class QuartzJobFactory implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokMethod(scheduleJob);
    }
}