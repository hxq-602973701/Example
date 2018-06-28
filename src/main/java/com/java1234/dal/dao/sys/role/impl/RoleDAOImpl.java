package com.java1234.dal.dao.sys.role.impl;

import com.java1234.dal.dao.base.impl.BaseDAOImpl;
import com.java1234.dal.dao.sys.role.RoleDAO;
import com.java1234.dal.entity.main.sys.role.Role;
import com.java1234.dal.mapper.main.sys.role.RoleMapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * RoleDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO {

    /**
     * RoleMapper
     */
    @Resource
    private RoleMapper roleMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<Role> getMapper() {
        return roleMapper;
    }
}