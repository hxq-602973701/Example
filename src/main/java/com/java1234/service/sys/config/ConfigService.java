package com.java1234.service.sys.config;

import com.java1234.dal.entity.main.sys.config.Config;
import com.java1234.service.base.BaseService;

/**
 * ConfigService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface ConfigService extends BaseService<Config> {

    /**
     * 保存系统参数
     *
     * @param param
     * @return
     */
    void saveConfig(Config param);
}