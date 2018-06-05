package com.java1234.dal.enums;

/**
 * 单位类型枚举
 *
 * @author carrot 2016/6/3
 * @version 1.0.0
 */
public enum DeptTypeEnum {

    /**
     * 公安部
     */
    MINISTRY(1),

    /**
     * 省厅
     */
    PROVINCE_DEPARTMENT(16),

    /**
     * 市局
     */
    CITY_BUREAU(32),

    /**
     * 区分局
     */
    DISTRICT_BUREAU(128),

    /**
     * 支队
     */
    DETACHMENT(256),

    /**
     * 县局
     */
    COUNTY_BUREAU(1024),

    /**
     * 科室、大队、机关单位
     */
    DEPARTMENT(4096),

    /**
     * 派出所
     */
    POLICE_STATION(8192);


    private final int code;

    DeptTypeEnum(int code) {
        this.code = code;
    }

    public static DeptTypeEnum getType(final int code) {
        for (DeptTypeEnum type : DeptTypeEnum.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("wrong " + DeptTypeEnum.class.getName());
    }

    public int getValue() {
        return this.code;
    }

}
