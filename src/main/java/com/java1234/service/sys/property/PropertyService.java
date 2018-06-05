package com.java1234.service.sys.property;

import com.java1234.dal.entity.main.sys.property.Property;
import com.java1234.service.base.BaseService;

import java.util.List;

/**
 * PropertyService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface PropertyService extends BaseService<Property> {

    /**
     * 根据parentId获取出所有的子属性列表
     *
     * @param param
     * @return
     */
    List<Property> getSubPropByParentId(Property param);
}