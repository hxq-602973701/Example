//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.java1234.exception.message;


public enum UserMessage implements BaseMessage {
    ACCOUNT_REPEAT("user.account.repeat", "用户帐户已经存在"),
    ACCOUNT_REQUIRED("user.account.required", "账户不能为空"),
    ACCOUNT_NOT_EXIST("user.account.not_exist", "用户帐户不存在"),
    ACCOUNT_CHECK("user.account.check", "账户长度必须在{1}到{2}之间"),
    PASSWORD_REQUIRED("user.password.required", "账户密码不能为空"),
    PASSWORD_MISS("user.password.miss", "账户密码不正确"),
    NICK_REPEAT("user.nick.repeat", "昵称已经存在"),
    PHONE_REPEAT("user.phone.repeat", "手机号码已经存在"),
    PHONE_NUMBER_ERROR("user.phone.error", "手机号码错误"),
    MAC_REQUIRED("user.mac.required", "客户端MAC地址不能为空"),
    SMS_VERIFY_ERROR("user.sms.verify.error", "手机验证码错误");

    private final String messageCode;
    private final String message;

    private UserMessage(String messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public String getMessageCode() {
        return this.messageCode;
    }

    public String getMessage() {
        return this.message;
    }
}
