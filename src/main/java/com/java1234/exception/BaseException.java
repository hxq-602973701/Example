//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.java1234.exception;

import com.java1234.exception.message.BaseMessage;

import java.text.MessageFormat;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 951860079180937477L;
    private final int errorCode;
    private final BaseMessage messageCode;

    public BaseException(String message) {
        super(message);
        this.errorCode = 500;
        this.messageCode = null;
    }

    public BaseException(int errorCode) {
        this.errorCode = errorCode;
        this.messageCode = null;
    }

    public BaseException(int errorCode, BaseMessage messageCode, Object... arg) {
        super(MessageFormat.format(messageCode.getMessage(), arg));
        this.errorCode = errorCode;
        this.messageCode = messageCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public BaseMessage getMessageCode() {
        return this.messageCode;
    }
}
