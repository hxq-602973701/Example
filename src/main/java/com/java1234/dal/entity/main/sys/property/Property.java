package com.java1234.dal.entity.main.sys.property;

import com.java1234.dal.entity.base.BaseEntity;
import com.java1234.dal.utils.SequenceRule;

import java.util.Date;
import javax.persistence.*;

@Table(name = "s_property")
@SequenceRule
public class Property extends BaseEntity {
    @Id
    @Column(name = "prop_id")
    private Integer propId;

    @Column(name = "prop_parent_id")
    private Integer propParentId;

    @Column(name = "prop_type")
    private Integer propType;

    @Column(name = "prop_key")
    private String propKey;

    @Column(name = "prop_value")
    private String propValue;

    @Column(name = "prop_icon")
    private String propIcon;

    @Column(name = "prop_memo")
    private String propMemo;

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

    /**
     * @return prop_id
     */
    public Integer getPropId() {
        return propId;
    }

    /**
     * @param propId
     */
    public void setPropId(Integer propId) {
        this.propId = propId;
    }

    /**
     * @return prop_parent_id
     */
    public Integer getPropParentId() {
        return propParentId;
    }

    /**
     * @param propParentId
     */
    public void setPropParentId(Integer propParentId) {
        this.propParentId = propParentId;
    }

    /**
     * @return prop_type
     */
    public Integer getPropType() {
        return propType;
    }

    /**
     * @param propType
     */
    public void setPropType(Integer propType) {
        this.propType = propType;
    }

    /**
     * @return prop_key
     */
    public String getPropKey() {
        return propKey;
    }

    /**
     * @param propKey
     */
    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    /**
     * @return prop_value
     */
    public String getPropValue() {
        return propValue;
    }

    /**
     * @param propValue
     */
    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    /**
     * @return prop_icon
     */
    public String getPropIcon() {
        return propIcon;
    }

    /**
     * @param propIcon
     */
    public void setPropIcon(String propIcon) {
        this.propIcon = propIcon;
    }

    /**
     * @return prop_memo
     */
    public String getPropMemo() {
        return propMemo;
    }

    /**
     * @param propMemo
     */
    public void setPropMemo(String propMemo) {
        this.propMemo = propMemo;
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
}