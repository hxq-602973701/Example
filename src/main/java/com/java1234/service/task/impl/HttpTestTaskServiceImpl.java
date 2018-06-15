package com.java1234.service.task.impl;


import com.java1234.service.task.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求
 */
public class HttpTestTaskServiceImpl extends BaseTaskService {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(HttpTestTaskServiceImpl.class);

    /**
     * @throws Throwable
     */
    @Override
    protected void execute() throws Throwable {


    }

    @Override
    protected Class<?> getSubClass() {
        return HttpTestTaskServiceImpl.class;
    }
}
