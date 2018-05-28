package com.java1234.web.interceptor;

import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 页面权限控制
 *
 * @author xinfeng.hu 2013-3-14下午1:44:07
 * @version 1.0.0
 */
public final class HandlerInterceptor extends BaseInterceptor {

    /**
     * 权限验证Service
     */
//    @Resource
//    private AuthService authService;

    /**
     * 请求前的预处理（进行编码、安全控制等处理）
     */
    @Override
    public boolean preHandleBase(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod == false) {
            return true;
        }

        final String requestURI = request.getRequestURI();

        //过滤IE11以下版本
//        final String userAgent = RequestUtil.getUserAgent().toLowerCase();
//        final String deviceType = RequestUtil.getRequest().getHeader("Device-Type");
//        if (StringUtils.isBlank(deviceType)
//                && (StringUtils.isBlank(userAgent) || (userAgent.indexOf("chrome") == -1 && !requestURI.startsWith("/upgrade_browser")))
//                && !"weijing".equals(userAgent)
//                ) {
//            response.sendRedirect("/upgrade_browser");
//            return false;
//        }

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();

        // 取得请求Controller方法的权限注释
//        final AuthCheck authCheck = method.getAnnotation(AuthCheck.class);

        // 权限验证
//        if (authCheck != null) {
//
//            //从请求参数[PC，Ajax]获取、没有的话从cookie[PC，Ajax]获取、没有的话从header[App]获取
//            String token = RequestUtil.getStringParameter(request, CookieUtil.TOKEN);
//            if (token == null) {
//                token = CookieUtil.getCookieValue(request, CookieUtil.SESSION_ID);
//            }
//            if (token == null) {
//                token = request.getHeader(CookieUtil.TOKEN);
//            }
//
//            // 权限验证
//            if (authService.verifyToken(token) == false && !authCheck.useUserOnly()) {
//                noLogin(response);
//                return false;
//            }
//
//            AuthTypeEnum[] authArray = null;//authCheck.auth();
//            if (authArray != null && authArray.length > 0) {
//
//                // 获取登录用户信息
//                final User user = LoginContext.getUser();
//
//                final Collection<AuthTypeEnum> authList = Lists.newArrayList(authArray);

                // 取交集 改为Long
                //authList.retainAll(user.getAuthType());

                // 没有权限返回 401
//                if (authList.size() == 0) {
//                    noAuth(response);
//                    return false;
//                }
//            }
//        }

        return true;
    }

}
