package com.java1234.web.listener;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackConfigListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(LogbackConfigListener.class);
    private static final String CONFIG_LOCATION = "logbackConfigLocation";

    public LogbackConfigListener() {
    }

    public void contextInitialized(ServletContextEvent event) {
        String logbackConfigLocation = event.getServletContext().getInitParameter("logbackConfigLocation");
        String contextPath = event.getServletContext().getRealPath("WEB-INF");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = event.getServletContext().getRealPath("") + "/WEB-INF";
        }

        String fn = contextPath + "/" + logbackConfigLocation;

        try {
            LoggerContext e = (LoggerContext) LoggerFactory.getILoggerFactory();
            e.reset();
            JoranConfigurator joranConfigurator = new JoranConfigurator();
            joranConfigurator.setContext(e);
            joranConfigurator.doConfigure(fn);
            logger.debug("loaded slf4j configure file from {}", fn);
        } catch (JoranException var7) {
            logger.error("can loading slf4j configure file from " + fn, var7);
        }

    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}
