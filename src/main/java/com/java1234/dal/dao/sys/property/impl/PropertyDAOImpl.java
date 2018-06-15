package com.java1234.dal.dao.sys.property.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java1234.dal.dao.base.impl.BaseDAOImpl;
import com.java1234.dal.dao.sys.property.PropertyDAO;
import com.java1234.dal.entity.main.sys.property.Property;
import com.java1234.dal.mapper.main.sys.property.PropertyMapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * PropertyDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class PropertyDAOImpl extends BaseDAOImpl<Property> implements PropertyDAO {

    /**
     * PropertyMapper
     */
    @Resource
    private PropertyMapper propertyMapper;

    /**
     * 系统参数缓存
     */
    public static List<Property> cacheList = null;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<Property> getMapper() {
        return propertyMapper;
    }

    /**
     * 根据param获取出所有的分页列表
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo<Property> selectPageByProperty(Property param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize(), true);
        param.setQuery("".equals(param.getQuery()) ? null : param.getQuery());
        List<Property> propertyList = propertyMapper.selectBySetMemberVariable(param);
        PageInfo<Property> pageInfo = new PageInfo<>(propertyList);
        return pageInfo;
    }

    /**
     * 获取缓存列表
     *
     * @return
     */
    @Override
    public List<Property> getCacheList() {
        if (PropertyDAOImpl.cacheList == null) {
            refreshCacheList();
        }
        return PropertyDAOImpl.cacheList;
    }

    /**
     * 更新缓存信息
     *
     * @return
     */
    public void refreshCacheList() {
        Property param = new Property();
        param.setDelFlag(false);
        PropertyDAOImpl.cacheList = propertyMapper.selectAreaList(param);
    }
}