package com.java1234.service.sys.config.impl;

import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.config.ConfigDAO;
import com.java1234.dal.entity.main.sys.config.Config;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.config.ConfigService;

import javax.annotation.Resource;

import com.java1234.util.Ids;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    /**
     * 保存系统参数
     *
     * @param param
     * @return
     */
    @Override
    public void saveConfig(Config param) {
        Assert.notNull(param, "param can not be null");

        final Integer configId = param.getConfigId();

        if (Ids.verifyId(configId)) {
            configDAO.updateByPrimaryKeySelective(param);
        } else {
            insertConfig(param);
        }
    }

    /**
     * 新增系统参数
     */
    public void insertConfig(Config param) {
        Assert.notNull(param, "param can not be null");
        Assert.notNull(param.getConfigKey(), "key can not be null");
        Assert.notNull(param.getConfigValue(), "value can not be null");

        param.setDelFlag(false);

        configDAO.insertSelective(param);
    }
}