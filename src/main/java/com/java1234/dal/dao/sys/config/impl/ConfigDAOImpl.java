package com.java1234.dal.dao.sys.config.impl;

import com.java1234.dal.dao.base.impl.BaseDAOImpl;
import com.java1234.dal.dao.sys.config.ConfigDAO;
import com.java1234.dal.entity.main.sys.config.Config;
import com.java1234.dal.mapper.main.sys.config.ConfigMapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * ConfigDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class ConfigDAOImpl extends BaseDAOImpl<Config> implements ConfigDAO {

    /**
     * ConfigMapper
     */
    @Resource
    private ConfigMapper configMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<Config> getMapper() {
        return configMapper;
    }
}