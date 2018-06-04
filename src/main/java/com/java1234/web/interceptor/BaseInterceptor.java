package com.java1234.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.java1234.dal.annotation.AuthCheck;
import com.java1234.dal.utils.CookieUtil;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.utils.LoginContext;
import com.java1234.util.RequestUtil;
import com.java1234.util.ResponseCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 权限控制拦截器
 *
 * @author lt 2018-06-01
 * @version 1.0.0
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {

    /**
     * 开始时间记录器
     */
    private static final ThreadLocal<Long> SLOW_REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

    /**
     * 响应数据日志记录器
     */
    private final Logger responseLogger = LoggerFactory.getLogger("response-data-log");

    /**
     * 慢请求日志记录器
     */
    private final Logger slowLogger = LoggerFactory.getLogger("slow-request-log");

    /**
     * 请求前的预处理（进行编码、安全控制等处理）
     */
    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 处理开始时间
        SLOW_REQUEST_THREAD_LOCAL.set(System.currentTimeMillis());

        // 请求前的预处理
        return preHandleBase(request, response, handler);
    }

    /**
     * 请求前的预处理（进行编码、安全控制等处理） 子类应该重写此方法
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandleBase(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Nothing(子类应该重写此方法)
        return true;
    }

    /**
     * 请求处理后的处理
     */
    @Override
    public final void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {

        // 请求处理后的处理(子类的处理方式)
        postHandleBase(request, response, handler, mv);

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();

        // 取得请求Controller方法的权限注释
        final AuthCheck authCheck = method.getAnnotation(AuthCheck.class);

        // 权限验证
        if (authCheck != null) {
            mv.addObject("pageId", authCheck.page());
        }

        if (mv != null) {

            final String token = LoginContext.getToken();

            List<Cookie> cookies = DataPipe.in(mv).meta(ResponseCode.SC_OK).cookies();

            // 保持Cookie到客户端
            if (cookies != null) {
                cookies.forEach(cookie -> response.addCookie(cookie));
            } else if (StringUtils.isNotBlank(token)) {

                //将cookie的过期时间设置为浏览器关闭时间
                Cookie cookie = CookieUtil.getSessionCookie(token);
                cookie.setMaxAge(-1);
                // 延长Session时间
                response.addCookie(cookie);
            }

            // 记录请求返回数据
            if (responseLogger.isDebugEnabled()) {
                final Long uid = LoginContext.getUserId();
                final String requestURI = request.getRequestURI();
                String queryString = request.getQueryString();
                if (StringUtils.isBlank(queryString)) {
                    queryString = JSON.toJSONString(request.getParameterMap());
                }
                String json = JSON.toJSONString(mv.getModel());
                responseLogger.debug("query url[{}],query[{}],login_id[{}],response:\n{}\n", requestURI, queryString, uid, json);
            }
        }
    }

    /**
     * 请求处理后的处理 子类应该重写此方法
     *
     * @param request
     * @param response
     * @param handler
     * @param mv
     * @return
     * @throws Exception
     */
    public void postHandleBase(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
        // Nothing(子类应该重写此方法)
    }

    /**
     * 请求处理完成后的处理
     */
    @Override
    public final void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        try {
            // 请求处理完成后的处理(子类的处理方式)
            afterCompletionBase(request, response, handler, ex);

            // 处理结束时间
            long overTimes = System.currentTimeMillis();
            // 处理时长
            long runtime = (overTimes - SLOW_REQUEST_THREAD_LOCAL.get());
            // 处理时长大于3000毫秒的处理将被记录
            if (runtime > 3000) {
                String requestURI = request.getRequestURI();
                String queryString = request.getQueryString();
                Long loginId = LoginContext.getUserId();
                slowLogger.error("query url[{}],query[{}],login_id[{}],runtime[{}ms]", requestURI, queryString, loginId, runtime);
            }

        } finally {
            // 清除本地线程缓存(最后的机会了)
            SLOW_REQUEST_THREAD_LOCAL.remove();
            LoginContext.remove();
        }

    }

    /**
     * 请求处理完成后的处理 子类应该重写此方法
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletionBase(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Nothing(子类应该重写此方法)
    }

    /**
     * 未登录时
     *
     * @param response
     * @throws IOException
     */
    protected void noLogin(HttpServletResponse response) throws IOException {
        if (RequestUtil.isJsonRequest()) {
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().println(JSON.toJSONString(new Object() {
                public Object meta = new Object() {
                    public int code = HttpServletResponse.SC_UNAUTHORIZED;
                    public String message = "您未登录系统，或您在其他地方登录了帐号。";
                };
            }));
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * 无权限时
     *
     * @param response
     * @throws IOException
     */
    protected void noAuth(HttpServletResponse response) throws IOException {
        if (RequestUtil.isJsonRequest()) {
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().println(JSON.toJSONString(new Object() {
                public Object meta = new Object() {
                    public int code = HttpServletResponse.SC_FORBIDDEN;
                    public String message = "您没有权限访问该页面。";
                };
            }));
        } else {
            response.sendRedirect("/401");
        }
    }

}
