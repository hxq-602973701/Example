package com.java1234.service.sys.dept.impl;

import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.dept.DeptDAO;
import com.java1234.dal.entity.main.sys.dept.Dept;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.dept.DeptService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * DeptService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {
    
    /**
     * DeptDAO
     */
    @Resource
    private DeptDAO deptDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<Dept> getDAO() {
        return deptDAO;
    }
}