package com.java1234.dal.entity.main.sys.token;

import com.java1234.dal.entity.base.BaseEntity;
import com.java1234.dal.enums.DeviceTypeEnum;

/**
 * S_TOKEN
 *
 * @author lt 2018/6/1
 * @version 1.0.0
 */
public class Token extends BaseEntity {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 3509292349187284355L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户令牌
     */
    private String token;

    /**
     * 设备平台
     */
    private DeviceTypeEnum deviceType;

    /**
     * 设备push token
     */
    private String pushToken;


    /**
     * @return USER_ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return TOKEN
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return DEVICE_TYPE
     */
    public DeviceTypeEnum getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType
     */
    public void setDeviceType(DeviceTypeEnum deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return PUSH_TOKEN
     */
    public String getPushToken() {
        return pushToken;
    }

    /**
     * @param pushToken
     */
    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }
}