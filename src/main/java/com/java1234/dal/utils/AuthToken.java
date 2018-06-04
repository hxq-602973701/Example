package com.java1234.dal.utils;



import com.java1234.dal.entity.main.sys.user.User;

import java.io.Serializable;

/**
 * 登录用户信息
 *
 * @author lt 2018/06/01
 * @version 1.0.0
 */
public class AuthToken implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 5315397165408601908L;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 用户信息
     */
    private User user;

    /**
     * 令牌
     */
    private String token;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
