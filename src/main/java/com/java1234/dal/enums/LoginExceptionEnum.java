package com.java1234.dal.enums;


import com.java1234.exception.message.BaseMessage;

/**
 * 对于登录的异常状态进行额外的枚举定义（枚举，特殊的类）
 */
public enum LoginExceptionEnum implements BaseMessage {
    //枚举更多的是实例对象创建在内部
    ACCOUNT_NOT_AUTH_LOGIN("user.account.not_authLogin", "对不起，当前你没有登录的权限，您可以与管理员取得联系！");

    private final String messageCode;
    private final String message;

    LoginExceptionEnum(String messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    @Override
    public String getMessageCode() {
        return this.messageCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
