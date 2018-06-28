//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.java1234.dal.enums;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum AuthTypeEnum {
    ADMIN(1),
    STAFF(2);

    private static final List<AuthTypeEnum> ENUM_LIST = Lists.newArrayList(values());
    private final int code;

    private AuthTypeEnum(int code) {
        this.code = code;
    }

    public static AuthTypeEnum getType(int code) {
        AuthTypeEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            AuthTypeEnum type = var1[var3];
            if (type.code == code) {
                return type;
            }
        }

        throw new IllegalArgumentException("wrong AuthTypeEnum");
    }

    public static List<AuthTypeEnum> getTypes(int code) {
        List<AuthTypeEnum> enumList = (List) ENUM_LIST.stream().filter((auth) -> {
            return (code & auth.code) == auth.code;
        }).collect(Collectors.toList());
        if (enumList != null && !enumList.isEmpty()) {
            return enumList;
        } else {
            throw new IllegalArgumentException("wrong AuthTypeEnum");
        }
    }

    public int getValue() {
        return this.code;
    }
}
