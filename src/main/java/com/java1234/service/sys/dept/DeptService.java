package com.java1234.service.sys.dept;

import com.java1234.dal.entity.main.sys.dept.Dept;
import com.java1234.dal.enums.DeptTypeEnum;
import com.java1234.service.base.BaseService;

import java.util.List;

/**
 * DeptService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface DeptService extends BaseService<Dept> {

    /**
     * 获取父节点下所有单位的树形结构列表
     *
     * @param branchId 单位ID
     * @return
     */
    Dept selectDeptTreeHierarchy(Integer branchId);


    /**
     * 获取指定单位ID下的所有列表(如果指定了单位类型则值返回指定的类型)
     *
     * @param deptId        单位ID
     * @param includeParent 是否包含自身
     * @param deptTypes     单位类型数组
     * @return
     */
    List<Dept> selectSubList(Integer deptId, boolean includeParent, DeptTypeEnum... deptTypes);

    /**
     * 保存单位信息
     *
     * @param dept
     */
    void saveDept(Dept dept);
}