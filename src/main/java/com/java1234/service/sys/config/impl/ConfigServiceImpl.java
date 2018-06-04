package com.java1234.service.sys.config.impl;

import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.config.ConfigDAO;
import com.java1234.dal.entity.main.sys.config.Config;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.config.ConfigService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * ConfigService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<Config> implements ConfigService {
    
    /**
     * ConfigDAO
     */
    @Resource
    private ConfigDAO configDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<Config> getDAO() {
        return configDAO;
    }
}