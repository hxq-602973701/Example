package com.java1234.dal.mapper.main.sys.property;

import com.java1234.dal.entity.main.sys.property.Property;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PropertyMapper extends Mapper<Property> {

    /**
     * 根据param获取出所有的分页列表
     *
     * @param param
     * @return
     */
    List<Property> selectBySetMemberVariable(Property param);
}