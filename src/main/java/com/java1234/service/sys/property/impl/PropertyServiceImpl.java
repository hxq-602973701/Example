package com.java1234.service.sys.property.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.property.PropertyDAO;
import com.java1234.dal.entity.main.sys.property.Property;
import com.java1234.dal.utils.LoginContext;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.property.PropertyService;

import javax.annotation.Resource;

import com.java1234.util.Ids;
import com.java1234.util.JsonBinder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据parentId获取出所有的子属性列表
     *
     * @param param
     * @return
     */
    @Override
    public List<Property> getSubPropByParentId(Property param) {

        Assert.notNull(param, "param not be null");

        return propertyDAO.select(param);
    }

    /**
     * 业务参数树结构
     *
     * @param param
     * @return
     */
    @Override
    public String selectPropTreeHierarchy(Property param) {

        List<Property> list = propertyDAO.select(new Property());
        //业务参数
        List<Map<String, Object>> treeList = Lists.newArrayList();
        list.stream().filter(node -> node.getDelFlag() == false).forEach(node -> {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", node.getPropId() + "");
            if (Ids.verifyId(node.getPropParentId())) {
                map.put("parent", node.getPropParentId() + "");
            } else {
                map.put("parent", "#");
            }
            String propValue = node.getPropValue();
            if (propValue != null && !"".equals(propValue)) {
                propValue = propValue.length() > 11 ? propValue.substring(0, 11) + "..." : propValue;
            }
            map.put("text", propValue);
            treeList.add(map);
        });

        return JsonBinder.toJson(treeList);
    }

    /**
     * 根据param获取出所有的分页列表
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo<Property> selectPageByProperty(Property param) {
        return propertyDAO.selectPageByProperty(param);
    }

    /**
     * 保存业务参数
     *
     * @param param
     */
    @Override
    public void saveProp(Property param) {

        Assert.notNull(param, "param not be null");

        if (param.getPropId() != null) {
            //修改
            param.setModifiedUid(LoginContext.getUserId());
            param.setModifiedTime(new Date());
            propertyDAO.updateByPrimaryKeySelective(param);
        } else {
            //添加
            propertyDAO.insertSelective(param);
        }

    }

    /**
     * 批量逻辑删除
     *
     * @param param
     */
    @Override
    public void deleteProp(Long[] param) {

        Assert.notNull(param.length, "param length not be 0");
        for (Long id : param) {

            Property property = new Property();
            property.setDelFlag(true);
            property.setPropId(id);
            property.setModifiedTime(new Date());
            property.setModifiedUid(LoginContext.getUserId());
            propertyDAO.updateByPrimaryKeySelective(property);
        }

    }
}