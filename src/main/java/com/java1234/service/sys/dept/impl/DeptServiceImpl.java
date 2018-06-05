package com.java1234.service.sys.dept.impl;

import com.google.common.collect.Lists;
import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.dept.DeptDAO;
import com.java1234.dal.entity.main.sys.dept.Dept;
import com.java1234.dal.enums.DeptTypeEnum;
import com.java1234.exception.DataErrorException;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.dept.DeptService;

import javax.annotation.Resource;

import com.java1234.util.CommonMessage;
import com.java1234.util.Ids;
import com.java1234.util.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 获取父节点下所有单位的树形结构列表
     *
     * @param branchId 单位ID
     * @return
     */
    @Override
    public Dept selectDeptTreeHierarchy(Integer branchId) {

        //获取所有的单位列表
        final List<Dept> deptList = selectAll();

        final Dept root = deptList.parallelStream()
                .filter(d -> Ids.eq(d.getDeptId(), branchId))
                .findFirst()
                .get();

        return builderHierarchy(root, deptList);
    }

    /**
     * 获取指定单位ID下的所有列表(如果指定了单位类型则值返回指定的类型)
     *
     * @param deptId        单位ID
     * @param deptTypes     单位类型数组
     * @param includeParent 是否包含自身
     * @return
     */
    @Override
    public List<Dept> selectSubList(Integer deptId, boolean includeParent, DeptTypeEnum... deptTypes) {

        //获取所有的单位列表
        List<Dept> deptList = selectAll();

        if (deptTypes != null && deptTypes.length > 0) {

            //把类型加起来
            long types = Lists.newArrayList(deptTypes)
                    .parallelStream()
                    .mapToLong(t -> t.getValue())
                    .sum();

            // 过滤类型
            deptList = deptList.stream()
                    .filter(d -> (d.getDeptType() & types) > 0)
                    .collect(Collectors.toList());
        }

        final Dept root = deptList.parallelStream()
                .filter(d -> Ids.eq(d.getDeptId(), deptId))
                .findFirst()
                .get();

        return builderSubList(root, deptList, includeParent);
    }

    /**
     * 保存单位信息
     *
     * @param param
     */
    @Override
    public void saveDept(Dept param) {
        Assert.notNull(param, "param not be null");

        if (Ids.verifyId(param.getDeptId())) {
            deptDAO.updateByPrimaryKeySelective(param);
        } else {
            Assert.notNull(param.getDeptCode(), "deptCode not be null");
            Assert.notNull(param.getDeptName(), "deptName not be null");
            Assert.notNull(param.getDeptShortName(), "deptShortName not be null");
            Assert.notNull(param.getDeptParentId(), "deptParentId not be null");
            Assert.notNull(param.getDeptType(), "deptType not be null");

            //判断该部门是否已经存在
            if (deptDAO.selectCount(new Dept(param.getDeptCode())) != 0) {
                throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, CommonMessage.COMMON_MESSAGE, "该单位已经存在");
            }
            deptDAO.insertSelective(param);
        }
    }

    /**
     * 构建单位的子单位列表
     *
     * @param dept          当前单位
     * @param deptList      单位列表
     * @param includeParent 是否包含自身
     * @return
     */
    private List<Dept> builderSubList(Dept dept, List<Dept> deptList, boolean includeParent) {
        Assert.notNull(dept, "d can not be null");
        Assert.notNull(deptList, "deptList can not be null");

        List<Dept> subList = Lists.newArrayList();

        //是否包含父级本身
        if (includeParent) {
            subList.add(dept);
        }

        deptList.stream()
                .filter(d -> Ids.eq(dept.getDeptId(), d.getDeptParentId()))
                .forEach(d -> {
                    subList.add(d);
                    subList.addAll(builderSubList(d, deptList, false));
                });

        return subList;
    }

    /**
     * 构建单位的子单位列表
     *
     * @param dept 当前单位
     * @param list 单位列表
     * @return
     */
    private Dept builderHierarchy(Dept dept, List<Dept> list) {
        Assert.notNull(dept, "dept can not be null");
        Assert.notNull(list, "list can not be null");

        list.stream()
                .filter(i -> Ids.compare(i.getDeptParentId(), dept.getDeptId()) == 0)
                .forEach(i -> {
                    if (dept.getDeptList() == null) {
                        dept.setDeptList(Lists.newLinkedList());
                    }
                    dept.getDeptList().add(builderHierarchy(i, list));
                });
        return dept;
    }

    /**
     * 获取所有的单位（已删除的除外）
     *
     * @return
     */

    public List<Dept> selectAll() {
        final Dept param = new Dept();
        param.setDelFlag(false);
        //TODO 后面上缓存
        return deptDAO.select(param);
    }
}