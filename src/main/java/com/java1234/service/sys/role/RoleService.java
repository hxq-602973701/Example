package com.java1234.service.sys.role;

import com.java1234.dal.entity.main.sys.role.Role;
import com.java1234.service.base.BaseService;

import java.util.List;

/**
 * RoleService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 获取所有的角色（已删除的除外）
     *
     * @return
     */
    List<Role> selectAll();
}