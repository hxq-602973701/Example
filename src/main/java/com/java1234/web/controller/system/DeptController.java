package com.java1234.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.java1234.dal.annotation.AuthCheck;
import com.java1234.dal.constant.page.SystemPageID;
import com.java1234.dal.entity.main.sys.dept.Dept;
import com.java1234.dal.entity.main.sys.property.Property;
import com.java1234.dal.enums.PropertyEnum;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.utils.LoginContext;
import com.java1234.service.sys.dept.DeptService;
import com.java1234.service.sys.property.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/6/4.
 */
@Controller
public class DeptController {

    /**
     * 单位管理Service
     */
    @Resource
    private DeptService deptService;

    /**
     * 业务属性Service
     */
    @Resource
    private PropertyService propertyService;

    /**
     * 单位管理列表
     *
     * @param model
     * @return
     */
    @AuthCheck(page = SystemPageID.DEPT_LIST)
    @RequestMapping(value = "/system/dept-list", method = RequestMethod.GET)
    public String deptListView(final Model model) {
        return "/system/dept/dept-list";
    }

    /**
     * 单位管理树结构
     *
     * @param model
     * @return
     */
    @AuthCheck(page = SystemPageID.DEPT_LIST)
    @RequestMapping(value = "/system/dept-tree", method = RequestMethod.GET)
    public void deptTreeApi(final Model model) {

        final Dept root = deptService.selectDeptTreeHierarchy(LoginContext.getBranchId());
        DataPipe.in(model).response(root);
    }

    /**
     * 单位管理列表
     *
     * @param model
     * @return
     */
    @AuthCheck(page = SystemPageID.DEPT_LIST)
    @RequestMapping(value = "/system/dept-page", method = RequestMethod.GET)
    public void deptPageApi(final Model model, Dept param) {

        //分页获取单位列表
        param.setDelFlag(false);
        final PageInfo<Dept> pageList = deptService.selectPage(param);
        DataPipe.in(model).response(pageList);
    }

    /**
     * 显示单位编辑页面
     *
     * @param model
     * @param deptId
     * @return
     */
    @AuthCheck(page = SystemPageID.DEPT_LIST)
    @RequestMapping(value = "/system/dept", method = RequestMethod.GET)
    public String deptDetailView(final Model model, Integer deptId, Integer deptParentId) {

        Property property = new Property();
        property.setPropParentId(Long.valueOf(PropertyEnum.DEPT_CONTROL.getValue()));
        List<Property> propertyList = propertyService.getSubPropByParentId(property);

        model.addAttribute("property", propertyList);

        //单位详情
        final Dept dept = deptService.selectByPrimaryKey(deptId);

        //单位列表
        final List<Dept> deptList = deptService.selectSubList(LoginContext.getBranchId(), true);

        model.addAttribute("dept", dept);
        model.addAttribute("deptList", deptList);

        if (dept == null) {
            model.addAttribute("deptParentId", deptParentId);
        } else {
            model.addAttribute("deptParentId", dept.getDeptParentId());
        }

        //如果父级单位是市局，则显示区分局和县局选项
        if(deptService.selectByPrimaryKey(deptParentId).getDeptType() < 128)
        {
            model.addAttribute("isCity", true);
        }

        return "/system/dept/dept-edit";
    }

    /**
     * 批量删除单位
     *
     * @param deptIds
     * @return
     */
    @AuthCheck(page = SystemPageID.DEPT_LIST)
    @RequestMapping(value = "/system/dept", method = RequestMethod.DELETE)
    public void deleteDeptsApi(Integer[] deptIds) {

        //批量删除角色
        deptService.deleteWithLogicByPrimaryKeys(deptIds, LoginContext.getUserId());
    }
    /**
     * 保存单位信息
     *
     * @param param
     * @return
     */
    @AuthCheck(page = SystemPageID.DEPT_LIST)
    @RequestMapping(value = "/system/dept", method = RequestMethod.POST)
    public void saveDeptApi(Dept param) {

        //操作用户
        param.setModifiedUid(LoginContext.getUserId());

        //保存单位
        deptService.saveDept(param);
    }
}
