package com.java1234.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.java1234.dal.annotation.AuthCheck;
import com.java1234.dal.constant.page.SystemPageID;
import com.java1234.dal.entity.main.sys.menu.Menu;
import com.java1234.dal.entity.main.sys.role.Role;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.utils.LoginContext;
import com.java1234.service.sys.menu.MenuService;
import com.java1234.service.sys.role.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */
@Controller
public class RoleController {

    /**
     * 系统角色Service
     */
    @Resource
    private RoleService roleService;

    /**
     * 菜单Service
     */
    @Resource
    private MenuService menuService;

    /**
     * 获取所有角色
     *
     * @return
     */
    @AuthCheck(page = SystemPageID.ROLE_LIST)
    @RequestMapping(value = "/system/role-list-all", method = RequestMethod.GET)
    public void roleListAllView(final Model model) {
        List<Role> roleList = roleService.selectAll();
        DataPipe.in(model).response(roleList);
    }

    /**
     * 显示角色管理列表页面
     *
     * @return
     */
    @AuthCheck(page = SystemPageID.ROLE_LIST)
    @RequestMapping(value = "/system/role-list", method = RequestMethod.GET)
    public String roleListView() {
        return "/system/role/role-list";
    }

    /**
     * 角色管理列表接口
     *
     * @param model
     * @param param
     */
    @AuthCheck(page = SystemPageID.ROLE_LIST)
    @RequestMapping(value = "/system/role-page", method = RequestMethod.GET)
    public void rolePageApi(final Model model, Role param) {

        //分页获取角色列表
        param.setDelFlag(false);
        final PageInfo<Role> pageList = roleService.selectPage(param);
        DataPipe.in(model).response(pageList);
    }

    /**
     * 显示角色编辑页面
     *
     * @param model
     * @param roleId
     * @return
     */
    @AuthCheck(page = SystemPageID.ROLE_LIST)
    @RequestMapping(value = "/system/role", method = RequestMethod.GET)
    public String roleDetailView(final Model model, Integer roleId) {

        final Role role = roleService.selectByPrimaryKey(roleId);

        model.addAttribute("role", role);

        return "/system/role/role-edit";
    }

    /**
     * 保存角色
     *
     * @param param
     * @return
     */
    @AuthCheck(page = SystemPageID.ROLE_LIST)
    @RequestMapping(value = "/system/role", method = RequestMethod.POST)
    public void saveRoleApi(Role param) {

        //操作用户
        param.setModifiedUid(LoginContext.getUserId());

        //保存角色
        roleService.saveRole(param);
    }

    /**
     * 批量删除角色
     *
     * @param roleIds
     * @return
     */
    @AuthCheck(page = SystemPageID.ROLE_LIST)
    @RequestMapping(value = "/system/role", method = RequestMethod.DELETE)
    public void deleteRolesApi(Integer[] roleIds) {

        //批量删除角色 TODO 菜单与用户角色需要去除，后期加上。
        roleService.deleteWithLogicByPrimaryKeys(roleIds, LoginContext.getUserId());
    }

    /**
     * 显示角色菜单页面
     *
     * @param model
     * @param roleId
     * @return
     */
    @AuthCheck(page = SystemPageID.ROLE_LIST)
    @RequestMapping(value = "/system/role-menu-tree", method = RequestMethod.GET)
    public String menuDetailView(final Model model, Integer roleId) {

        final Role role = roleService.selectByPrimaryKey(roleId);
        model.addAttribute("role", role);

        return "/system/role/role-menu";
    }

    /**
     * 根据需要新增和删除的menuIds，更新选中角色的菜单权限
     *
     * @param model
     * @param authType
     * @param addMenu
     * @param delMenu
     * @return
     */
    @AuthCheck(page = SystemPageID.MENU_LIST)
    @RequestMapping(value = "/system/menu/update", method = RequestMethod.POST)
    public void updateMenuApi(final Model model, String authType, String addMenu, String delMenu) {

        Menu menu = new Menu();
        //新增用户权限(必须在删除之前，因为获取的数组可能有重复)
        if (!addMenu.equals("undefined")) {
            String addM[] = addMenu.split(",");
            for (String addIndex : addM) {
                menu = menuService.selectMenuByPageId(addIndex);
                //赋予权限
                menu.setMenuRoles(Long.parseLong(authType) | menu.getMenuRoles());
                //保存数据(批量?)
                menuService.updateByPrimaryKey(menu);
            }
        }

        if (!delMenu.equals("undefined")) {
            //删除用户权限
            String delM[] = delMenu.split(",");
            for (String delIndex : delM) {
                menu = menuService.selectMenuByPageId(delIndex);
                //赋予权限
                menu.setMenuRoles(~Long.parseLong(authType) & (menu.getMenuRoles()));
                //保存数据(批量?)
                menuService.updateByPrimaryKey(menu);
            }
        }
    }
}
