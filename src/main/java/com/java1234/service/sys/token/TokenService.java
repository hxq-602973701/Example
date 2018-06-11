package com.java1234.service.sys.token;

import com.java1234.dal.entity.main.sys.token.Token;
import com.java1234.dal.enums.DeviceTypeEnum;
import com.java1234.service.base.BaseService;

/**
 * TokenService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface TokenService {

    /**
     * 根据Uid构建Token
     *
     * @param userId
     * @param deviceType
     * @return
     */
    Token getToken(Long userId, DeviceTypeEnum deviceType);


    /**
     * 根据token获取Token对象
     *
     * @param token
     * @return
     */
    Token selectToken(String token);

    /**
     * 失效指定Token
     *
     * @param token
     */
    void invalidToken(String token);
}