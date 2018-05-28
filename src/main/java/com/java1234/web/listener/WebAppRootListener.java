package com.java1234.web.listener;

import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 注册Web默认路径
 *
 * @author lt 2018/05/28
 * @version 1.0.0
 */
public class WebAppRootListener implements ServletContextListener {

    /**
     * Web app root key parameter at the servlet context level
     * (i.e. a context-param in {@code web.xml}): "webAppRootKey".
     */
    public static final String WEB_APP_ROOT_KEY_PARAM = "webAppRootKey";

    /**
     * Default web app root key: "webapp.root"
     */
    public static final String DEFAULT_WEB_APP_ROOT_KEY = "webapp.root";

    /**
     * Default web app root key: "webapp.resource"
     */
    public static final String WEB_APP_ROOT_RESOURCE_KEY = "webapp.resource";

    @Override
    public void contextInitialized(ServletContextEvent event) {

        final ServletContext servletContext = event.getServletContext();

        final String root = servletContext.getRealPath("/");
        if (root == null) {
            throw new IllegalStateException("Cannot set web app root system property when WAR file is not expanded");
        }
        final String param = servletContext.getInitParameter(WEB_APP_ROOT_KEY_PARAM);
        final String key = (param != null ? param : DEFAULT_WEB_APP_ROOT_KEY);
        final String oldValue = System.getProperty(key);
        if (oldValue != null && !StringUtils.pathEquals(oldValue, root)) {
            throw new IllegalStateException(
                    "Web app root system property already set to different value: '" +
                            key + "' = [" + oldValue + "] instead of [" + root + "] - " +
                            "Choose unique values for the 'webAppRootKey' context-param in your web.xml files!");
        }
        System.setProperty(key, root);
        System.setProperty(WEB_APP_ROOT_RESOURCE_KEY, root + "/statics");
        servletContext.log("Set web app root system property: '" + key + "' = [" + root + "]");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        ServletContext servletContext = event.getServletContext();

        final String param = servletContext.getInitParameter(WEB_APP_ROOT_KEY_PARAM);
        final String key = (param != null ? param : DEFAULT_WEB_APP_ROOT_KEY);
        System.getProperties().remove(key);
        System.getProperties().remove(WEB_APP_ROOT_RESOURCE_KEY);
    }

}
