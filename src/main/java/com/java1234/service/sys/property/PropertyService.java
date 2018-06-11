package com.java1234.service.sys.property;

import com.github.pagehelper.PageInfo;
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

    /**
     * 业务参数树结构
     *
     * @param param
     * @return
     */
    String selectPropTreeHierarchy(Property param);

    /**
     * 根据param获取出所有的分页列表
     *
     * @param param
     * @return
     */
    PageInfo<Property> selectPageByProperty(Property param);

    /**
     * 保存业务参数
     *
     * @param param
     */
    void saveProp(Property param);

    /**
     * 批量逻辑删除
     *
     * @param param
     */
    void deleteProp(Long[] param);
}