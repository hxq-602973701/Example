package com.java1234.dal.dao.common;


import com.java1234.dal.entity.base.BaseEntity;
import com.java1234.dal.enums.DataSourceEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用DAO
 *
 * @author lt on 2016/6/3.
 * @version 1.0.0
 */
public interface CommonDAO {

    /**
     * 填充序列值
     *
     * @param entity 实体对象
     */
    void fillSequence(final BaseEntity entity);

    /**
     * 根据条件获取指定表的数据
     * <p>
     *
     * @param dataSource   数据源
     * @param tableName    表名
     * @param conditionMap 条件
     * @param limit        最多返回条数
     * @return
     */
    List<HashMap> selectByCondition(DataSourceEnum dataSource, String tableName, Map conditionMap, Integer limit);

    /**
     * 根据条件更新指定表的记录
     *
     * @param dataSource   数据源
     * @param tableName    表名
     * @param updateMap    更新内容
     * @param conditionMap 条件
     */
    int updateByCondition(DataSourceEnum dataSource, String tableName, Map updateMap, Map conditionMap);

}
