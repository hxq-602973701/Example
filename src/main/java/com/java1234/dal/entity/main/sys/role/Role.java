package com.java1234.dal.entity.main.sys.role;

import com.java1234.dal.entity.base.BaseEntity;
import com.java1234.dal.utils.SequenceRule;

import java.util.Date;
import javax.persistence.*;

@Table(name = "s_role")
@SequenceRule
public class Role extends BaseEntity {
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "auth_type")
    private Long authType;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_memo")
    private String roleMemo;

    private Integer ranking;

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
     * @return role_id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return branch_id
     */
    public Long getBranchId() {
        return branchId;
    }

    /**
     * @param branchId
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
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
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return role_memo
     */
    public String getRoleMemo() {
        return roleMemo;
    }

    /**
     * @param roleMemo
     */
    public void setRoleMemo(String roleMemo) {
        this.roleMemo = roleMemo;
    }

    /**
     * @return ranking
     */
    public Integer getRanking() {
        return ranking;
    }

    /**
     * @param ranking
     */
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
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