package com.java1234.service.sys.role.impl;

import com.github.pagehelper.PageHelper;
import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.role.RoleDAO;
import com.java1234.dal.entity.main.sys.role.Role;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.role.RoleService;
import com.java1234.util.Ids;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.OptionalLong;

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
     *
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

    /**
     * 保存角色
     *
     * @param param
     * @return
     */
    @Override
    public void saveRole(Role param) {
        Assert.notNull(param, "param can not be null");

        final Integer roleId = param.getRoleId();

        if (Ids.verifyId(roleId)) {
            roleDAO.updateByPrimaryKeySelective(param);
        } else {
            insertRole(param);
        }
    }

    /**
     * 添加角色
     *
     * @param entity
     */
    public void insertRole(final Role entity) {
        Assert.notNull(entity, "entity can not be null");
        Assert.notNull(entity.getRoleName(), "roleName can not be null");

        //获取最大的AuthType
        final OptionalLong maxAuthType = selectAll().stream().mapToLong(r -> r.getAuthType()).max();

        //存在则：最大值 * 2， 不存在则：为1
        if (maxAuthType.isPresent()) {
            entity.setAuthType(maxAuthType.getAsLong() * 2);
        } else {
            entity.setAuthType(1L);
        }

        entity.setDelFlag(false);
        roleDAO.insertSelective(entity);
    }
}