package com.java1234.web.controller.mq;

import com.java1234.service.activemq.ConsumerService;
import com.java1234.service.activemq.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

/**
 * Created by Administrator on 2017/1/5.
 */
@Controller
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource(name = "queueDestination")
    private Destination destination;

    /***
     *  队列消息生产者
     */
    @Resource
    private ProducerService producerService;

    /**
     * 队列消息消费者
     */
    @Resource
    private ConsumerService consumerService;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public void send(String msg) {
        logger.info(Thread.currentThread().getName() + "------------send to jms Start");
        producerService.sendMessage(msg);
        logger.info(Thread.currentThread().getName() + "------------send to jms End");
    }

    @RequestMapping(value = "/receiveMessage", method = RequestMethod.GET)
    @ResponseBody
    public Object receive() {
        logger.info(Thread.currentThread().getName() + "------------receive from jms Start");
        TextMessage tm = consumerService.receive(destination);
        logger.info(Thread.currentThread().getName() + "------------receive from jms End");
        return tm;
    }

}