package com.java1234.dal.dao.sys.open.impl;

import com.java1234.dal.dao.base.impl.BaseDAOImpl;
import com.java1234.dal.dao.sys.open.OpenThirdInDAO;
import com.java1234.dal.entity.main.sys.open.OpenThirdIn;
import com.java1234.dal.mapper.main.sys.open.OpenThirdInMapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * OpenThirdInDAO
 *
 * @author lt 2018-6-14
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class OpenThirdInDAOImpl extends BaseDAOImpl<OpenThirdIn> implements OpenThirdInDAO {

    /**
     * OpenThirdInMapper
     */
    @Resource
    private OpenThirdInMapper openThirdInMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<OpenThirdIn> getMapper() {
        return openThirdInMapper;
    }
}