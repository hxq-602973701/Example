package com.java1234.exception;

import com.alibaba.fastjson.JSON;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.utils.LoginContext;
import com.java1234.exception.message.BaseMessage;
import com.java1234.util.ResponseCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 错误解析器
 *
 * @author lt 2018-06-15
 * @version 1.0.0
 */
public class HandlerExceptionResolver implements org.springframework.web.servlet.HandlerExceptionResolver {

    /**
     * 日志记录器
     */
    private final Logger logger = LoggerFactory.getLogger(HandlerExceptionResolver.class);

    /**
     * 响应数据日志记录器
     */
    private final Logger responseLogger = LoggerFactory.getLogger("response-data-log");

    /**
     * 异常解决
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        final ModelAndView mav = new ModelAndView();
        int code = ResponseCode.SC_INTERNAL_SERVER_ERROR;
        BaseMessage messageCode = null;
        String message = "服务器开小差了，请稍后重试！";
        // 为空直接返回500
        if (ex != null) {

            // 原始错误信息
            String errorMessage = ex.getMessage() == null ? ex.toString() : ex.getMessage();

            if (ex instanceof HttpRequestMethodNotSupportedException) {
                code = ResponseCode.SC_METHOD_NOT_ALLOWED;
            } else {
                // 保留原始异常
                Throwable e = ex;
                do {
                    if (e instanceof DataErrorException) {
                        code = ((DataErrorException) e).getErrorCode();
                        messageCode = ((DataErrorException) e).getMessageCode();
                        message = errorMessage;
                        ex = null;
                        break;
                    }
                    e = e.getCause();
                } while (e != null && e != e.getCause());
            }
        }

        // 记录日志
        logger.error(request.getRequestURI() + ":" + message, ex);

        // 设置meta信息
        DataPipe.in(mav).meta(code, messageCode, message);

        // 记录请求返回数据
        if (responseLogger.isDebugEnabled()) {
            String requestURI = request.getRequestURI();
            String queryString = request.getQueryString();
            if (StringUtils.isBlank(queryString)) {
                queryString = JSON.toJSONString(request.getParameterMap());
            }
            Long loginId = LoginContext.getUserId();
            String json = JSON.toJSONString(mav.getModel());
            responseLogger.debug("query url[{}],query[{}],login_id[{}],response:\n{}\n", requestURI, queryString, loginId, json);
        }

        return mav;
    }
}
