package com.java1234.dal.dao.base.impl;


import com.java1234.dal.dao.base.BaseDAO;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.common.Mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 通用DAO基类,其他DAO继承该基类即可
 *
 * @author carrot on 2015/2/3.
 * @version 1.0.0
 */
public abstract class BaseDAOImpl<T> implements BaseDAO<T> {

    /**
     * 日志记录器
     */
    protected static final Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);


    /**
     * 获取Mapper
     *
     * @return
     */
    protected abstract Mapper<T> getMapper();

    /**
     * 只获取一条数据，当查询的结果多于一条数据时抛出异常。如果只想获取检索结果的第一条数据请一部 @see selectFirst
     *
     * @param record
     * @return
     */
    public T selectOne(T record) {
        final List<T> dataList = getMapper().select(record);
        final int size = dataList.size();
        if (size == 1) {
            return dataList.get(0);
        } else if (size > 1) {
            throw new TooManyResultsException(String.format("预期获取一行数据，实际为%s行", dataList.size()));
        } else {
            return null;
        }
    }

    /**
     * 只获取检索出来的第一条数据,忽略其他数据。
     *
     * @param record
     * @return
     */
    public T selectFirst(T record) {
        final List<T> dataList = getMapper().select(record);
        if (dataList.size() > 0) {
            return dataList.get(0);
        } else {
            return null;
        }
    }



    /**
     * 根据条件查询返回数据列表
     *
     * @param record
     * @return
     */
    @Override
    public List<T> select(T record) {
        return getMapper().select(record);
    }

    /**
     * 根据条件查询返回数据条数
     *
     * @param record
     * @return
     */
    @Override
    public int selectCount(T record) {
        return getMapper().selectCount(record);
    }

    /**
     * 根据主键查询
     *
     * @param pk 主键
     * @return
     */
    @Override
    public T selectByPrimaryKey(Object pk) {
        if (pk == null) {
            return null;
        }
        return getMapper().selectByPrimaryKey(pk);
    }

    /**
     * 插入，空属性也会插入
     *
     * @param record
     * @return
     */
    @Override
    public int insert(T record) {
        return getMapper().insert(record);
    }

    /**
     * 插入，空属性不会插入
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(T record) {

        return getMapper().insertSelective(record);
    }


    /**
     * 根据条件删除
     *
     * @param key
     * @return
     */
    @Override
    public int delete(T key) {
        return getMapper().delete(key);
    }

    /**
     * 根据主键删除
     *
     * @param pk
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Object pk) {
        return getMapper().deleteByPrimaryKey(pk);
    }


    /**
     * 根据主键修改，空值条件会修改成null
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(T record) {

        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据主键修改，空值条件不会修改成null
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(T record) {

        return getMapper().updateByPrimaryKeySelective(record);
    }

    /**
     * 获取泛型类Class
     *
     * @return
     */
    private Class<T> genericType() {
        Type genType = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<T>) params[0];
    }

}
