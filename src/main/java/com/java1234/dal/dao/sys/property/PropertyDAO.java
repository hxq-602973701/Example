package com.java1234.dal.dao.sys.property;

import com.github.pagehelper.PageInfo;
import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.entity.main.sys.property.Property;

import java.util.List;

/**
 * PropertyDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface PropertyDAO extends BaseDAO<Property> {

    /**
     * 根据param获取出所有的分页列表
     *
     * @param param
     * @return
     */
    PageInfo<Property> selectPageByProperty(Property param);

    /**
     * 获取缓存列表
     *
     * @return
     */
    List<Property> getCacheList();
}