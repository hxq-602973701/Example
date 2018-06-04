package com.java1234.dal.vo.sys;


import com.java1234.dal.entity.main.sys.user.User;

import java.io.Serializable;

public class UserVO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = -7702945813597829139L;

    /**
     * 用户令牌
     */
    private String token;

    /**
     * 用户实体
     */
    private User user;

    /**
     * 跳转菜单路径
     */
    private String redirectUrl;

    /**
     * 构造函数
     */
    public UserVO() {
        // Nothing
    }

    /**
     * 构造函数
     */
    public UserVO(final String token) {
        this(token, null);
    }

    /**
     * 构造函数
     *
     * @param token 用户令牌
     * @param user  用户实体
     */
    public UserVO(final String token, final User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
