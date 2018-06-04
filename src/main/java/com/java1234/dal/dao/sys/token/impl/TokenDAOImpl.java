package com.java1234.dal.dao.sys.token.impl;

import com.java1234.dal.dao.sys.token.TokenDAO;
import com.java1234.dal.entity.main.sys.token.Token;
import com.java1234.dal.enums.DeviceTypeEnum;
import com.java1234.dal.mapper.main.sys.token.TokenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * TokenDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class TokenDAOImpl implements TokenDAO {

    /**
     * 日志记录器
     */
    private final Logger logger = LoggerFactory.getLogger(TokenDAOImpl.class);

    /**
     * TokenMapper
     */
    @Resource
    private TokenMapper tokenMapper;

    /**
     * 根据Uid和设备平台查看是否有Token
     *
     * @param userId     用户id
     * @param deviceType 设备平台
     * @return
     */
    @Override
    public Token selectTokenByUserId(Long userId, DeviceTypeEnum deviceType) {
        return tokenMapper.selectTokenByUserId(userId, deviceType);
    }

    /**
     * 保存Token实体到数据库
     *
     * @param token 令牌信息
     */
    @Override
    public void insertToken(Token token) {
        Assert.notNull(token, "token entity can not be null");

        // 持久化数据
        int result = tokenMapper.insertToken(token);
        if (result != 1) {
            logger.error("insert token error! uid={}", token.getUserId());
        }
    }

    /**
     * 更新令牌信息
     *
     * @param token 令牌信息
     */
    @Override
    public void updateToken(Token token) {
        Assert.notNull(token, "token entity can not be null");

        // 更新数据
        int result = tokenMapper.updateToken(token);
        // 失效缓存
        // cache.delete(tokenDO.getToken());
        if (result != 1) {
            logger.error("update token error! userId={}", token.getUserId());
        }
    }

    /**
     * 根据Token获取Token对象
     *
     * @param token
     * @return
     */
    @Override
    public Token selectToken(final String token) {
        Assert.notNull(token, "token can not be null");

        // 获取令牌对象，如果不存在则从数据库恢复
        Token entity = tokenMapper.selectToken(token);

        return entity;
    }

}