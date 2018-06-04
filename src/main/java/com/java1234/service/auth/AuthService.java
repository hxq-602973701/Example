package com.java1234.service.auth;


import com.java1234.dal.entity.main.sys.token.Token;

/**
 * 权限验证
 *
 * @author lt 2018-06-1
 * @version 1.0.0
 */
public interface AuthService {

    /**
     * 用户权限验证
     *
     * @param token
     * @return
     */
    public boolean verifyToken(String token);

    /**
     * 用户权限验证(WebSocket)
     *
     * @param token
     * @return
     */
    public Token verifyTokenByWebSocket(String token);

}
