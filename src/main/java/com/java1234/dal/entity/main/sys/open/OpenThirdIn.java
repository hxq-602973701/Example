package com.java1234.dal.entity.main.sys.open;

import com.java1234.dal.entity.base.BaseEntity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "o_open_third_in")
public class OpenThirdIn extends BaseEntity {
    @Id
    @Column(name = "open_id")
    private Integer openId;

    @Column(name = "app_key")
    private String appKey;

    @Column(name = "app_secrety")
    private String appSecrety;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "app_memo")
    private String appMemo;

    @Column(name = "dept_code")
    private String deptCode;

    @Column(name = "dept_name")
    private String deptName;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    private Boolean delFlag;

    /**
     * 创建人
     */
    @Column(name = "create_uid")
    private Long createUid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "modified_uid")
    private Long modifiedUid;

    /**
     * 修改时间
     */
    @Column(name = "modified_time")
    private Date modifiedTime;

    /**
     * @return open_id
     */
    public Integer getOpenId() {
        return openId;
    }

    /**
     * @param openId
     */
    public void setOpenId(Integer openId) {
        this.openId = openId;
    }

    /**
     * @return app_key
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * @return app_secrety
     */
    public String getAppSecrety() {
        return appSecrety;
    }

    /**
     * @param appSecrety
     */
    public void setAppSecrety(String appSecrety) {
        this.appSecrety = appSecrety;
    }

    /**
     * @return app_name
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return app_memo
     */
    public String getAppMemo() {
        return appMemo;
    }

    /**
     * @param appMemo
     */
    public void setAppMemo(String appMemo) {
        this.appMemo = appMemo;
    }

    /**
     * @return dept_code
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * @param deptCode
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * @return dept_name
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取删除标志
     *
     * @return del_flag - 删除标志
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标志
     *
     * @param delFlag 删除标志
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取创建人
     *
     * @return create_uid - 创建人
     */
    public Long getCreateUid() {
        return createUid;
    }

    /**
     * 设置创建人
     *
     * @param createUid 创建人
     */
    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改人
     *
     * @return modified_uid - 修改人
     */
    public Long getModifiedUid() {
        return modifiedUid;
    }

    /**
     * 设置修改人
     *
     * @param modifiedUid 修改人
     */
    public void setModifiedUid(Long modifiedUid) {
        this.modifiedUid = modifiedUid;
    }

    /**
     * 获取修改时间
     *
     * @return modified_time - 修改时间
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifiedTime 修改时间
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}