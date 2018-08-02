package com.java1234.dal.dao.schedulejob.impl;

import com.java1234.dal.dao.base.impl.BaseDAOImpl;
import com.java1234.dal.dao.schedulejob.ScheduleJobDAO;
import com.java1234.dal.entity.main.schedulejob.ScheduleJob;
import com.java1234.dal.mapper.main.schedulejob.ScheduleJobMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * ScheduleJobDAO
 *
 * @author lt 2018-8-2
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class ScheduleJobDAOImpl extends BaseDAOImpl<ScheduleJob> implements ScheduleJobDAO {
    
    /**
     * ScheduleJobMapper
     */
    @Resource
    private ScheduleJobMapper scheduleJobMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<ScheduleJob> getMapper() {
        return scheduleJobMapper;
    }
}