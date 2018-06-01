package com.java1234.dal.dao.common.impl;


import com.java1234.dal.dao.common.CommonDAO;
import com.java1234.dal.entity.base.BaseEntity;
import com.java1234.dal.mapper.common.CommonMapper;
import com.java1234.dal.utils.SequenceRule;
import com.java1234.dal.enums.DataSourceEnum;
import com.java1234.util.DateUtil;
import com.java1234.util.MultipleDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通用DAO
 *
 * @author lt on 2016/6/3
 * @version 1.0.0
 */
@Service
public class CommonDAOImpl implements CommonDAO {

    /**
     * Get属性方法索引
     */
    private static final int GET_METHOD = 0;
    /**
     * Set属性方法索引
     */
    private static final int SET_METHOD = 1;
    /**
     * 日志记录器
     */
    private final Logger logger = LoggerFactory.getLogger(CommonDAOImpl.class);
    /**
     * 通用Mapper
     */
    @Resource
    private CommonMapper commonMapper;

    /**
     * 缓存的待生成主键的类字段【Method[] = getMethod、setMethod】
     */
    private Map<Class<?>, Method[]> methodCacheMap = new ConcurrentHashMap<>();

    /**
     * 填充序列值
     *
     * @param entity 实体对象
     */
    @Override
    public void fillSequence(BaseEntity entity) {
        Assert.notNull(entity, "clazz can not be null");

        final Class<? extends BaseEntity> clazz = entity.getClass();
        final SequenceRule sequenceRule = clazz.getAnnotation(SequenceRule.class);

        //是否标记了序列自定义生成。
        if (sequenceRule != null) {

            //获取序列
            final Long nextval = sequenceNextval(clazz);

            //get set属性方法
            Method[] idMethods = methodCacheMap.get(clazz);

            //恢复缓存的属性方法
            if (idMethods == null) {
                //查找ID字段
                final Field idField = querySequenceRuleAnnotationField(clazz, Id.class);

                //判断主键字段是否设置
                if (idField == null) {
                    throw new IllegalArgumentException("未找到主键字段：" + clazz.getName());
                }

                // 获取get set 属性方法。
                final Class<?> fieldType = idField.getType();
                final String fieldName = idField.getName();
                final String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    idMethods = new Method[2];
                    idMethods[GET_METHOD] = clazz.getMethod("get" + methodName);
                    idMethods[SET_METHOD] = clazz.getMethod("set" + methodName, fieldType);

                    methodCacheMap.put(clazz, idMethods);
                } catch (NoSuchMethodException e) {
                    throw new IllegalArgumentException("未找到主键字段的属性方法：" + clazz.getName() + "." + idField.getName(), e);
                }
            }

            try {
                Object result = idMethods[GET_METHOD].invoke(entity);
                //属性已经赋值的情况下直接返回。
                if (result != null) {
                    logger.debug("属性已经赋值不再设置序列：" + clazz.getName() + "." + idMethods[GET_METHOD].getName() + "=" + result);
                    return;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("获取属性字段值错误：" + clazz.getName() + "." + idMethods[GET_METHOD].getName(), e);
            }

            //设置生成的主键
            try {
                final Class<?> parameterType = idMethods[SET_METHOD].getParameterTypes()[0];
                if (parameterType.equals(Long.class)) {
                    idMethods[SET_METHOD].invoke(entity, nextval);
                } else if (parameterType.equals(Integer.class)) {
                    idMethods[SET_METHOD].invoke(entity, nextval.intValue());
                } else if (parameterType.equals(Double.class)) {
                    idMethods[SET_METHOD].invoke(entity, nextval.doubleValue());
                } else if (parameterType.equals(Float.class)) {
                    idMethods[SET_METHOD].invoke(entity, nextval.floatValue());
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("设置属性字段值错误：" + clazz.getName() + "." + idMethods[SET_METHOD].getName(), e);
            }
        }
    }

    /**
     * 获取指定实体对应的表的序列值
     *
     * @param clazz 数据库实体的Clazz
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long sequenceNextval(Class<? extends BaseEntity> clazz) {
        Assert.notNull(clazz, "clazz can not be null");

        //设置数据源
        MultipleDataSource.setDataSourceKey(DataSourceEnum.MAIN);

        //序列生成规则注解
        final SequenceRule sequenceRule = clazz.getAnnotation(SequenceRule.class);
        if (sequenceRule == null) {
            throw new IllegalArgumentException("未找到序列规则注解：" + clazz.getName());
        }

        //序列名规则
        final String nameRule = transferRule(clazz, sequenceRule.nameRule(), clazz.getSimpleName());

        //序列值规则
        final String valRule = transferRule(clazz, sequenceRule.valRule(), null);

        // 获取序列
        long nextval = commonMapper.sequenceNextval(nameRule);

        // 适配规则
        if (StringUtils.isNotBlank(valRule)) {
            nextval = Long.parseLong(valRule) + nextval;
        }

        return nextval;
    }

    /**
     * 获取需要生成主键策略的字段
     *
     * @param clazz
     * @return
     */
    private Field querySequenceRuleAnnotationField(final Class clazz, final Class<? extends Annotation> annotation) {
        final Field[] fields = clazz.getDeclaredFields();
        Field sequenceField = null;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getAnnotation(annotation) != null) {
                sequenceField = fields[i];
                break;
            }
        }
        return sequenceField;
    }

    /**
     * 根据规则生成内容
     *
     * @param clazz  实体Class
     * @param rule   规则
     * @param prefix 前缀
     * @return
     */
    private String transferRule(final Class<? extends BaseEntity> clazz, final String rule, final String prefix) {

        String value = "";
        //根据规则拼接
        if (StringUtils.isNotBlank(rule)) {
            final String[] roles = rule.split("\\|");
            for (int i = 0; i < roles.length; i++) {
                if (StringUtils.isBlank(roles[i])) {
                    continue;
                }
                final String[] role = roles[i].split(":");
                if (role.length == 1) {
                    value = value + role[0];
                } else if ("date".equalsIgnoreCase(role[0])) {
                    value = value + DateUtil.format(new Date(), role[1]);
                } else if ("number".equalsIgnoreCase(role[0])) {
                    value = value + StringUtils.leftPad("", Integer.parseInt(role[1]), "0");
                } else {
                    throw new IllegalArgumentException("格式未被定义：" + clazz.getName() + "[SequenceRule:" + role[0] + "]。  支持格式为:【date:format[|number:n]】");
                }
            }
        }

        //添加前缀
        if (StringUtils.isNotBlank(prefix)) {
            value = prefix + (StringUtils.isNotBlank(value) ? ("_" + value) : value);
        }

        return value;
    }

    /**
     * 根据条件获取指定表的数据
     *
     * @param dataSource   数据源
     * @param tableName    表名
     * @param conditionMap 条件
     * @param limit        最多返回条数
     * @return
     */
    @Override
    public List<HashMap> selectByCondition(final DataSourceEnum dataSource, final String tableName, final Map conditionMap, final Integer limit) {
        MultipleDataSource.setDataSourceKey(dataSource);
        return commonMapper.selectByCondition(dataSource.getValue().toString(), tableName, conditionMap, limit);
    }

    /**
     * 根据条件更新指定表的记录
     *
     * @param dataSource   数据源
     * @param tableName    表名
     * @param updateMap    更新内容
     * @param conditionMap 条件
     */
    @Override
    public int updateByCondition(final DataSourceEnum dataSource, final String tableName, final Map updateMap, final Map conditionMap) {
        MultipleDataSource.setDataSourceKey(dataSource);
        return commonMapper.updateByCondition(tableName, updateMap, conditionMap);
    }

}
