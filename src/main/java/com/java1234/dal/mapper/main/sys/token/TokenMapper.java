package com.java1234.dal.mapper.main.sys.token;

import com.java1234.dal.entity.main.sys.token.Token;
import com.java1234.dal.enums.DeviceTypeEnum;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface TokenMapper extends Mapper<Token> {
    /**
     * 根据Uid和设备平台查看是否有Token
     *
     * @param userId     用户id
     * @param deviceType 设备平台
     * @return
     */
    Token selectTokenByUserId(@Param("userId") Long userId, @Param("deviceType") DeviceTypeEnum deviceType);

    /**
     * 持久化令牌信息
     *
     * @param token 令牌信息
     * @return
     */
    int insertToken(Token token);

    /**
     * 更新令牌信息
     *
     * @param token 令牌信息
     * @return
     */
    int updateToken(Token token);

    /**
     * 根据Token获取Token对象
     *
     * @param token
     * @return
     */
    Token selectToken(String token);
}