package com.java1234.dal.dao.sys.dept.impl;

import com.java1234.dal.dao.base.impl.BaseDAOImpl;
import com.java1234.dal.dao.sys.dept.DeptDAO;
import com.java1234.dal.entity.main.sys.dept.Dept;
import com.java1234.dal.mapper.main.sys.dept.DeptMapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * DeptDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class DeptDAOImpl extends BaseDAOImpl<Dept> implements DeptDAO {

    /**
     * DeptMapper
     */
    @Resource
    private DeptMapper deptMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<Dept> getMapper() {
        return deptMapper;
    }
}