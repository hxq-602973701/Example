package com.java1234.dal.entity.main.sys.user;

import com.java1234.dal.entity.base.BaseEntity;
import com.java1234.dal.entity.main.sys.dept.Dept;
import com.java1234.dal.utils.SequenceRule;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "s_user")
@SequenceRule
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "account")
    private String account;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "id_card_num")
    private String idCardNum;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "signature")
    private String signature;

    @Column(name = "auth_type")
    private Long authType;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "login_way")
    private Integer loginWay;

    @Column(name = "title")
    private String title;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "device_type")
    private Integer deviceType;

    @Column(name = "push_token")
    private String pushToken;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "mac")
    private String mac;

    @Column(name = "reg_ip")
    private String regIp;

    @Column(name = "last_ip")
    private String lastIp;

    @Column(name = "login_sum")
    private Long loginSum;

    @Column(name = "branch_id")
    private Integer branchId;

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

    //==============================================

    /**
     * 单位信息
     */
    @Transient
    private Dept dept;

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return id_card_num
     */
    public String getIdCardNum() {
        return idCardNum;
    }

    /**
     * @param idCardNum
     */
    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return auth_type
     */
    public Long getAuthType() {
        return authType;
    }

    /**
     * @param authType
     */
    public void setAuthType(Long authType) {
        this.authType = authType;
    }

    /**
     * @return dept_id
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * @param deptId
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    /**
     * @return login_way
     */
    public Integer getLoginWay() {
        return loginWay;
    }

    /**
     * @param loginWay
     */
    public void setLoginWay(Integer loginWay) {
        this.loginWay = loginWay;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return device_type
     */
    public Integer getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType
     */
    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return push_token
     */
    public String getPushToken() {
        return pushToken;
    }

    /**
     * @param pushToken
     */
    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    /**
     * @return device_name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @return reg_ip
     */
    public String getRegIp() {
        return regIp;
    }

    /**
     * @param regIp
     */
    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    /**
     * @return last_ip
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * @param lastIp
     */
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    /**
     * @return login_sum
     */
    public Long getLoginSum() {
        return loginSum;
    }

    /**
     * @param loginSum
     */
    public void setLoginSum(Long loginSum) {
        this.loginSum = loginSum;
    }

    /**
     * @return branch_id
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * @param branchId
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
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

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }
}