package com.java1234.service.sys.property.impl;

import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.property.PropertyDAO;
import com.java1234.dal.entity.main.sys.property.Property;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.property.PropertyService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * PropertyService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class PropertyServiceImpl extends BaseServiceImpl<Property> implements PropertyService {
    
    /**
     * PropertyDAO
     */
    @Resource
    private PropertyDAO propertyDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<Property> getDAO() {
        return propertyDAO;
    }
}