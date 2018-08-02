package com.java1234.web.controller.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

@Component
public class TaskTest {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(TaskTest.class);

    public void run() {
        for (int i = 0; i < 1; i++) {

            System.out.println("run......................run...........run...........");
            System.out.println("run......................run...........run...........");
            System.out.println("run......................run...........run...........");
        }
    }
    public void run1() {
        for (int i = 0; i < 1; i++) {
            System.out.println("run1......................run1...........run1...........");
            System.out.println("run1......................run1...........run1...........");
            System.out.println("run1......................run1...........run1...........");
        }
    }
}
