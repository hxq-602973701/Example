package com.java1234.dal.utils;


import com.java1234.dal.entity.main.sys.open.OpenThirdIn;
import com.java1234.dal.entity.main.sys.user.User;

/**
 * 登录信息
 *
 * @author lt 2018-06-14
 * @version 1.0.0
 */
public class LoginContext {

    /**
     * 用户登录信息
     */
    private static final ThreadLocal<AuthToken> TOKEN_THREAD_LOCAL = new ThreadLocal<>();


    /**
     * 第三方登录信息
     */
    private static final ThreadLocal<OpenThirdIn> OPEN_THIRD_IN_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public static AuthToken get() {
        return TOKEN_THREAD_LOCAL.get();
    }

    /**
     * 设置登录用户信息
     *
     * @param at
     */
    public static void set(AuthToken at) {
        TOKEN_THREAD_LOCAL.set(at);
    }

    /**
     * 清理本地线程缓存
     */
    public static void remove() {
        TOKEN_THREAD_LOCAL.remove();
    }


    /**
     * 获取第三方用户信息
     *
     * @return`
     */
    public static OpenThirdIn getOpenThirdIn() {
        return OPEN_THIRD_IN_THREAD_LOCAL.get();
    }

    /**
     * 设置第三方用户信息
     *
     * @param oti
     */
    public static void setOpenThirdIn(OpenThirdIn oti) {
        OPEN_THIRD_IN_THREAD_LOCAL.set(oti);
    }

    /**
     * 清理第三方在本地线程缓存
     */
    public static void removeOpenThirdIn() {
        OPEN_THIRD_IN_THREAD_LOCAL.remove();
    }

    /**
     * 获取登录的用户ID
     *
     * @return
     */
    public static Long getUserId() {
        AuthToken authToken = TOKEN_THREAD_LOCAL.get();
        if (authToken == null) {
            return 0L;
        }
        return authToken.getUid();
    }

    /**
     * 获取登录用户的分县局ID
     *
     * @return
     */
    public static Integer getBranchId() {
        return getUser().getBranchId();
    }

    /**
     * 获取登录用户的权限类型
     *
     * @return
     */
    public static Long getAuthType() {
        final User user = getUser();
        return user == null ? 0L : user.getAuthType();
    }

    /**
     * 获取登录的用户的Token
     *
     * @return
     */
    public static String getToken() {
        AuthToken authToken = TOKEN_THREAD_LOCAL.get();
        if (authToken == null) {
            return "";
        }
        return authToken.getToken();
    }

    /**
     * 获取登录的用户信息
     *
     * @return
     */
    public static User getUser() {
        AuthToken authToken = TOKEN_THREAD_LOCAL.get();
        if (authToken == null) {
            return null;
        }
        return authToken.getUser();
    }

}
