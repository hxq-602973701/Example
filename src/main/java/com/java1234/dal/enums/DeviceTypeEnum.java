package com.java1234.dal.enums;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public enum DeviceTypeEnum {
    WEB(0),
    IOS(1),
    ANDROID(2),
    WIN_PHONE(3),
    BB10(4),
    WIN_DESK_TOP(5),
    MOBILE(6),
    UNKNOWN(99);

    private final int code;

    private DeviceTypeEnum(int code) {
        this.code = code;
    }

    public static DeviceTypeEnum getType(int code) {
        DeviceTypeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            DeviceTypeEnum type = var1[var3];
            if (type.code == code) {
                return type;
            }
        }

        throw new IllegalArgumentException("wrong DeviceTypeEnum");
    }

    public int getValue() {
        return this.code;
    }
}
