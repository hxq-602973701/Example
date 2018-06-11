package com.java1234.service.sys.token.impl;

import com.java1234.dal.dao.sys.token.TokenDAO;
import com.java1234.dal.entity.main.sys.token.Token;
import com.java1234.dal.enums.DeviceTypeEnum;
import com.java1234.dal.security.login.MD5;
import com.java1234.service.sys.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * TokenService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class TokenServiceImpl implements TokenService {

    /**
     * TokenDAO
     */
    @Resource
    private TokenDAO tokenDAO;

    /**
     * 根据Uid构建Token,不存在创建，存在则更新
     *
     * @param userId     用户id
     * @param deviceType 设备平台
     * @return
     */
    @Override
    public Token getToken(Long userId, DeviceTypeEnum deviceType) {


        Assert.notNull(userId, "userId cannot be null");

        // 查看数据库中是否有Token
        Token token = tokenDAO.selectTokenByUserId(userId, deviceType);

        if (token == null) {

            // 创建
            token = new Token();
            token.setUserId(userId);
            token.setDeviceType(deviceType);
            token.setPushToken(null);
            token.setToken(createToken(userId));
            tokenDAO.insertToken(token);

        } else {

            // 更新
            token.setDeviceType(deviceType);
            token.setToken(createToken(userId));
            tokenDAO.updateToken(token);

        }
        return token;
    }

    /**
     * 根据token获取Token对象
     *
     * @param token
     * @return
     */
    @Override
    public Token selectToken(String token) {
        Assert.notNull(token, "token can not be null");
        return tokenDAO.selectToken(token);
    }

    /**
     * 失效指定Token
     *
     * @param token
     */
    @Override
    public void invalidToken(String token) {
        Assert.notNull(token, "token can not be null");

        tokenDAO.deleteToken(token);
    }

    /**
     * 生成令牌
     *
     * @param uid 用户id
     * @return
     */
    private static String createToken(Long uid) {
        Assert.notNull(uid, "uid can not be null");
        // 用户ID + 系统毫秒 + 加密Key + 纳秒
        return MD5.md5(uid.toString() + System.currentTimeMillis() + "--!z9fbvKL3d--" + System.nanoTime());
    }
}