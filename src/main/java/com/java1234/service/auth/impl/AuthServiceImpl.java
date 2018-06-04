package com.java1234.service.auth.impl;

import com.java1234.dal.entity.main.sys.token.Token;
import com.java1234.dal.entity.main.sys.user.User;
import com.java1234.dal.utils.AuthToken;
import com.java1234.dal.utils.LoginContext;
import com.java1234.service.auth.AuthService;
import com.java1234.service.sys.token.TokenService;
import com.java1234.service.sys.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.i18n.Exception;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 权限验证
 *
 * @author lt 2016-06-1
 * @version 1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public final class AuthServiceImpl implements AuthService {

    /**
     * 用户令牌Service
     */
    @Resource
    private TokenService tokenService;

    /**
     * 用户Service
     */
    @Resource
    private UserService userService;

    /**
     * 权限验证
     *
     * @param token
     * @return
     */
    @Override
    public boolean verifyToken(String token) {

        // 非空判断
        if (StringUtils.isBlank(token)) {
            return false;
        }

        // 判断Token是否存在
        Token tokenDO = tokenService.selectToken(token);

        if (tokenDO == null) {
            return false;
        }

        // 用户信息
        User user = userService.selectUserById(tokenDO.getUserId());

        if (user == null || user.getDelFlag()) {
            return false;
        }

        AuthToken authToken = new AuthToken();
        authToken.setUid(tokenDO.getUserId());
        authToken.setToken(tokenDO.getToken());
        authToken.setUser(user);

        // 设置登录信息
        LoginContext.set(authToken);

        // 成功
        return true;
    }

    /**
     * 用户权限验证(WebSocket)
     *
     * @param token
     * @return
     */
    @Override
    public Token verifyTokenByWebSocket(String token) {

        // 非空判断
        if (StringUtils.isBlank(token)) {
            return null;
        }

        // 判断Token是否存在
        Token tokenDO = tokenService.selectToken(token);

        // 成功
        return tokenDO;
    }

}
