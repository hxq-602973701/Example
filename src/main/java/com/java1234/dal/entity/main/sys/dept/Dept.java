package com.java1234.dal.entity.main.sys.dept;

import com.java1234.dal.entity.base.BaseEntity;
import com.java1234.dal.utils.SequenceRule;

import java.util.Date;
import javax.persistence.*;

@Table(name = "s_dept")
@SequenceRule
public class Dept extends BaseEntity {
    @Id
    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "dept_parent_id")
    private Long deptParentId;

    @Column(name = "dept_code")
    private String deptCode;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "dept_short_name")
    private String deptShortName;

    @Column(name = "dept_type")
    private Long deptType;

    private Integer ranking;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    private Byte delFlag;

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

    @Column(name = "dept_area")
    private String deptArea;

    /**
     * @return dept_id
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * @return dept_parent_id
     */
    public Long getDeptParentId() {
        return deptParentId;
    }

    /**
     * @param deptParentId
     */
    public void setDeptParentId(Long deptParentId) {
        this.deptParentId = deptParentId;
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
     * @return dept_short_name
     */
    public String getDeptShortName() {
        return deptShortName;
    }

    /**
     * @param deptShortName
     */
    public void setDeptShortName(String deptShortName) {
        this.deptShortName = deptShortName;
    }

    /**
     * @return dept_type
     */
    public Long getDeptType() {
        return deptType;
    }

    /**
     * @param deptType
     */
    public void setDeptType(Long deptType) {
        this.deptType = deptType;
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
    public Byte getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标志
     *
     * @param delFlag 删除标志
     */
    public void setDelFlag(Byte delFlag) {
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

    /**
     * @return dept_area
     */
    public String getDeptArea() {
        return deptArea;
    }

    /**
     * @param deptArea
     */
    public void setDeptArea(String deptArea) {
        this.deptArea = deptArea;
    }
}