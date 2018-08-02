package com.java1234.web.controller.task;

import com.java1234.dal.entity.main.schedulejob.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author lt
 * @Description: 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * @date 2018-08-01
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokMethod(scheduleJob);
    }
}