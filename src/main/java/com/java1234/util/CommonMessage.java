//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.java1234.util;


import com.java1234.exception.message.BaseMessage;

public enum CommonMessage implements BaseMessage {
    COMMON_MESSAGE("common.common_message", "{0}"),
    UPLOAD_FILE_FAILURE("common.upload_file.failure", "上传文件出错"),
    REQUEST_PARAM_REQUIRED("common.request_param.required", "缺少请求参数[{0}]"),
    ILLEGAL_PARAM_REQUIRED("common.request_param.illegal", "请求参数非法[{0}]"),
    PASSWORD_EQUAL("common.password.equal", "新密码必须不同于旧密码"),
    IMG_CODE_ERROR("common.img_code.error", "图片验证码错误");

    private final String messageCode;
    private final String message;

    private CommonMessage(String messageCode, String message) {
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
