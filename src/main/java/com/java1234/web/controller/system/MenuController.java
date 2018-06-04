package com.java1234.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.java1234.dal.annotation.AuthCheck;
import com.java1234.dal.constant.page.SystemPageID;
import com.java1234.dal.entity.main.sys.menu.Menu;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.utils.LoginContext;
import com.java1234.exception.DataErrorException;
import com.java1234.service.sys.menu.MenuService;
import com.java1234.service.sys.role.RoleService;
import com.java1234.util.CommonMessage;
import com.java1234.util.ResponseCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/6/1.
 */
@Controller
public class MenuController {

    /**
     * 系统菜单Service
     */
    @Resource
    private MenuService menuService;

    /**
     * 系统角色Service
     */
    @Resource
    private RoleService roleService;

    /**
     * 系统菜单列表
     *
     * @param model
     * @return
     */
    @AuthCheck(page = SystemPageID.MENU_LIST)
    @RequestMapping(value = "/system/menu-list", method = RequestMethod.GET)
    public String menuListView(final Model model) {

        //角色列表
        model.addAttribute("roleList", roleService.selectAll());

        return "/system/menu/menu-list";
    }

    /**
     * 系统菜单树结构
     *
     * @param model
     * @return
     */
    @AuthCheck(page = SystemPageID.MENU_LIST)
    @RequestMapping(value = "/system/menu-tree", method = RequestMethod.GET)
    public void menuTreeApi(final Model model, Integer show) {

        //获取层级的系统菜单(根据不同的权限参数)
        final List<Menu> treeList = menuService.selectMenuTreeHierarchy(LoginContext.getAuthType(), show);

        DataPipe.in(model).response(treeList);
    }

    /**
     * 系统菜单详情
     *
     * @param model
     * @return
     */
    @AuthCheck(page = SystemPageID.MENU_LIST)
    @RequestMapping(value = "/system/menu/{menuId}", method = RequestMethod.GET)
    public void menuDetailApi(final Model model, @PathVariable Long menuId) {

        DataPipe.in(model).response(menuService.selectByPrimaryKey(menuId));
    }

    /**
     * 保存系统菜单树的层级结构与顺序
     *
     * @param model
     * @param menuListJson
     */
    @AuthCheck(page = SystemPageID.MENU_LIST)
    @RequestMapping(value = "/system/menu-tree", method = RequestMethod.PUT)
    public void menuTreeApi(final Model model, String menuListJson) {

        if (StringUtils.isNotBlank(menuListJson)) {

            final List<Menu> menuList = JSON.parseArray(menuListJson, Menu.class);
            final Long uid = LoginContext.getUserId();

            menuList.stream().forEach(m->m.setModifiedUid(uid));

            //保存菜单树的层级结构与顺序
            menuService.saveMenuTreeHierarchy(menuList);
        }
    }

    /**
     * 保存系统菜单详情
     *
     * @param model
     * @return
     */
    @AuthCheck(page = SystemPageID.MENU_LIST)
    @RequestMapping(value = "/system/menu", method = RequestMethod.POST)
    public void saveMenuApi(final Model model, Menu menu) {

        //操作用户
        menu.setModifiedUid(LoginContext.getUserId());

        //保存菜单
        menuService.saveMenu(menu);
    }

    /**
     * 删除系统菜单
     *
     * @param model
     * @param menuIds
     * @return
     */
    @AuthCheck(page = SystemPageID.MENU_LIST)
    @RequestMapping(value = "/system/menu", method = RequestMethod.DELETE)
    public void delMenuApi(final Model model, Long[] menuIds) {
        if (menuIds == null || menuIds.length == 0) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, CommonMessage.COMMON_MESSAGE, "缺少菜单ID");
        }
        DataPipe.in(model).response(menuService.deleteWithLogicByPrimaryKeys(menuIds, LoginContext.getUserId()));
    }
}
