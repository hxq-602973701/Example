package com.java1234.service.sys.role.impl;

import com.github.pagehelper.PageHelper;
import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.role.RoleDAO;
import com.java1234.dal.entity.main.sys.menu.Menu;
import com.java1234.dal.entity.main.sys.role.Role;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.role.RoleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RoleService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    
    /**
     * RoleDAO
     */
    @Resource
    private RoleDAO roleDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<Role> getDAO() {
        return roleDAO;
    }

    /**
     * 获取所有权限
     * @return
     */
    @Override
    public List<Role> selectAll() {
        final Role param = new Role();
        param.setDelFlag(false);

        //按照 RANKING 排序
        PageHelper.orderBy("ranking");
        return roleDAO.select(param);
    }
}