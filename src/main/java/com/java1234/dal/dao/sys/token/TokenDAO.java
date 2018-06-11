package com.java1234.dal.dao.sys.token;

import com.java1234.dal.entity.main.sys.token.Token;
import com.java1234.dal.enums.DeviceTypeEnum;

/**
 * TokenDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface TokenDAO {
    /**
     * 根据Uid和设备平台查看是否有Token
     *
     * @param userId     用户id
     * @param deviceType 设备平台
     * @return
     */
    Token selectTokenByUserId(Long userId, DeviceTypeEnum deviceType);

    /**
     * 保存Token到数据库
     *
     * @param token 令牌信息
     */
    void insertToken(Token token);

    /**
     * 更新令牌信息
     *
     * @param token 令牌信息
     */
    void updateToken(Token token);

    /**
     * 根据Token获取Token对象
     *
     * @param token
     * @return
     */
    Token selectToken(String token);

    /**
     * 删除Token
     *
     * @param token
     */
    void deleteToken(String token);
}