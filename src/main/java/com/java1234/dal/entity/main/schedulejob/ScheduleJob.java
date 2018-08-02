package com.java1234.dal.entity.main.schedulejob;

import com.java1234.dal.entity.base.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "task_schedule_job")
public class ScheduleJob extends BaseEntity {

    /**
     * 开始运行
     */
    public static final String STATUS_RUNNING = "1";

    /**
     * 没有运行
     */
    public static final String STATUS_NOT_RUNNING = "0";

    /**
     * 同步
     */
    public static final String CONCURRENT_IS = "1";

    /**
     * 不同步
     */
    public static final String CONCURRENT_NOT = "0";

    @Id
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_group")
    private String jobGroup;

    @Column(name = "job_status")
    private String jobStatus;

    @Column(name = "cron_expression")
    private String cronExpression;

    private String description;

    @Column(name = "bean_class")
    private String beanClass;

    /**
     * 1
     */
    @Column(name = "is_concurrent")
    private String isConcurrent;

    @Column(name = "spring_id")
    private String springId;

    @Column(name = "method_name")
    private String methodName;

    /**
     * 修改时间
     */
    @Column(name = "modified_time")
    private Date modifiedTime;

    /**
     * 创建人
     */
    @Column(name = "create_uid")
    private Long createUid;

    /**
     * 修改人
     */
    @Column(name = "modified_uid")
    private Long modifiedUid;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    private Boolean delFlag;

    /**
     * @return job_id
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * @param jobId
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return job_name
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return job_group
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * @param jobGroup
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * @return job_status
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * @param jobStatus
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * @return cron_expression
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * @param cronExpression
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return bean_class
     */
    public String getBeanClass() {
        return beanClass;
    }

    /**
     * @param beanClass
     */
    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 获取1
     *
     * @return is_concurrent - 1
     */
    public String getIsConcurrent() {
        return isConcurrent;
    }

    /**
     * 设置1
     *
     * @param isConcurrent 1
     */
    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    /**
     * @return spring_id
     */
    public String getSpringId() {
        return springId;
    }

    /**
     * @param springId
     */
    public void setSpringId(String springId) {
        this.springId = springId;
    }

    /**
     * @return method_name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
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
}