//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.java1234.exception;


import com.java1234.exception.message.BaseMessage;

public class DataErrorException extends BaseException {
    private static final long serialVersionUID = 951860079180937477L;

    public DataErrorException() {
        super(500);
    }

    public DataErrorException(int errorCode) {
        super(errorCode);
    }

    public DataErrorException(int errorCode, BaseMessage messageCode, Object... arg) {
        super(errorCode, messageCode, arg);
    }
}
