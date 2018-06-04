package com.java1234.service.sys.menu;

import com.java1234.dal.entity.main.sys.menu.Menu;
import com.java1234.service.base.BaseService;

import java.util.List;

/**
 * MenuService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 获取第一个有权限的菜单
     *
     * @param authType 权限
     * @return
     */
    Menu selectFirstAuthMenu(Long authType);

    /**
     * 获取模块化的子系统列表
     *
     * @param authType
     * @return
     */
    List<Menu> selectSubSystem(Long authType);

    /**
     * 获取层级的系统菜单(根据不同的权限参数)
     *
     * @param authType 权限
     * @return
     */
    List<Menu> selectMenuTreeHierarchy(Long authType, Integer show);

    /**
     * 获取头部导航条
     *
     * @param authType
     * @param pageId
     * @return
     */
    List<Menu> selectNavbarList(Long authType, String pageId);

    /**
     * 获取左侧导航条列表
     *
     * @param authType 权限
     * @param pageId   页面ID
     * @return
     */
    List<Menu> selectSidebarList(Long authType, String pageId);

    /**
     * 获取面包屑导航条列表
     *
     * @param authType 权限
     * @param pageId   页面ID
     * @return
     */
    List<Menu> selectBreadcrumbBarList(Long authType, String pageId);

    /**
     * 保存菜单树的层级结构与顺序
     *
     * @param menuList
     */
    void saveMenuTreeHierarchy(List<Menu> menuList);

    /**
     * 保存菜单信息
     *
     * @param menu
     */
    void saveMenu(Menu menu);
}