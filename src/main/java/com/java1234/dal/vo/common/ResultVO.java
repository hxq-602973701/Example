package com.java1234.dal.vo.common;

import java.io.Serializable;

/**
 * 结果
 *
 * @author lt 2018-06-08
 * @version 1.0.0
 */
public class ResultVO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = -1768944629033384084L;

    /**
     * 执行结果
     */
    private Boolean success;

    /**
     * 是否存在
     */
    private Boolean exist;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

}
