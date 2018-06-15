package com.java1234.service.sys.open.impl;

import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.open.OpenThirdInDAO;
import com.java1234.dal.entity.main.sys.open.OpenThirdIn;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.open.OpenThirdInService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * OpenThirdInService
 *
 * @author lt 2018-6-14
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class OpenThirdInServiceImpl extends BaseServiceImpl<OpenThirdIn> implements OpenThirdInService {
    
    /**
     * OpenThirdInDAO
     */
    @Resource
    private OpenThirdInDAO openThirdInDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<OpenThirdIn> getDAO() {
        return openThirdInDAO;
    }
}