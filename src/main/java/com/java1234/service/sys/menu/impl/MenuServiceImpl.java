package com.java1234.service.sys.menu.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.menu.MenuDAO;
import com.java1234.dal.entity.main.sys.menu.Menu;
import com.java1234.exception.DataErrorException;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.menu.MenuService;
import com.java1234.util.CommonMessage;
import com.java1234.util.Ids;
import com.java1234.util.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * MenuService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    /**
     * MenuDAO
     */
    @Resource
    private MenuDAO menuDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<Menu> getDAO() {
        return menuDAO;
    }

    /**
     * 菜单顶层开始数
     */
    private static final Long TOP_LEVEL_NUM = 0L;

    /**
     * 获取第一个有权限的菜单
     *
     * @param authType 权限
     * @return
     */
    @Override
    public Menu selectFirstAuthMenu(Long authType) {
        //所有的菜单列表
        final List<Menu> menuList = selectAll();

        //有权限的菜单列表
        final List<Menu> authMenuList = menuList.parallelStream()
                .filter(m -> (m.getMenuRoles() & authType) != 0)
                .collect(Collectors.toList());

        //获取顶层模块
        final Menu firstSubSystemMenu = authMenuList.parallelStream()
                .filter(m -> m.getMenuParentId().equals(TOP_LEVEL_NUM))
                .findFirst()
                .get();

        return findLeafNode(firstSubSystemMenu, authMenuList);
    }

    /**
     * 获取模块化的子系统列表
     *
     * @param authType
     * @return
     */
    @Override
    public List<Menu> selectSubSystem(Long authType) {
        //所有的菜单列表
        final List<Menu> menuList = selectAll();

        //获取顶层模块，权限请在页面上处理
        List<Menu> subSystemList = menuList.stream()
                .filter(m -> m.getMenuShow())
                .filter(m -> m.getMenuParentId().equals(TOP_LEVEL_NUM))
                .collect(Collectors.toList());

        //有权限的菜单列表
        final List<Menu> authMenuList = menuList.parallelStream()
                .filter(m -> m.getMenuShow())
                .filter(m -> (m.getMenuRoles() & authType) != 0)
                .collect(Collectors.toList());

        // 判断智能跳转开关，设置点击菜单时连接到第一个子菜单页面
        subSystemList.parallelStream().filter(m -> Boolean.TRUE.equals(m.getSmartLink())).forEach(m -> {
            m.setMenuUrl(findLeafNode(m, authMenuList).getMenuUrl());
        });

        return subSystemList;
    }

    /**
     * 获取层级的系统菜单(根据不同的权限参数)
     *
     * @param authType 权限
     * @return
     */
    @Override
    public List<Menu> selectMenuTreeHierarchy(Long authType, Integer show) {

        //获取所有的菜单（已删除的除外）
        final List<Menu> menuList = selectAll();
        final List<Menu> menuListByAuth;

        //根据角色过滤菜单
        if (menuList != null) {
            //并行的方式处理权限过滤
            //show == 1 时，把隐藏的菜单过滤
            if (show != null) {
                menuListByAuth = menuList
                        .parallelStream()
                        .filter(m -> m.getMenuShow() == true)
                        .sorted((m1, m2) -> Ids.compare(m1.getRanking(), m2.getRanking()))
                        .collect(Collectors.toList());
            } else {
                //show == null 时，不过滤排序即可
                menuListByAuth = menuList
                        .parallelStream()
                        .sorted((m1, m2) -> Ids.compare(m1.getRanking(), m2.getRanking()))
                        .collect(Collectors.toList());
            }
        } else {
            menuListByAuth = menuList;
        }

        //获取顶层菜单，然后填充子菜单
        List<Menu> treeList = menuList.stream().filter(i -> Ids.compare(i.getMenuParentId(), TOP_LEVEL_NUM) == 0).collect(Collectors.toList());
        treeList.forEach(m -> builderHierarchy(m, menuListByAuth));
        return treeList;
    }

    /**
     * 获取头部导航条
     *
     * @param authType
     * @param pageId
     * @return
     */
    @Override
    public List<Menu> selectNavbarList(Long authType, String pageId) {
        //获取面包屑列表防止迷路
        final List<Menu> breadcrumbBarList = selectBreadcrumbBarList(authType, pageId);

        //第一个面包屑
        final Menu breadcrumbMenu = breadcrumbBarList.get(1);

        final Long menuId = breadcrumbMenu.getMenuParentId();

        //有权限的菜单列表
        final List<Menu> authMenuList = selectAll().stream()
                .filter(m -> m.getMenuShow())
                .filter(m -> (m.getMenuRoles() & authType) != 0)
                .collect(Collectors.toList());

        //顶部导航条列表
        final List<Menu> navbarList = authMenuList.stream()
                .filter(m -> Ids.eq(m.getMenuParentId(), menuId))
                .collect(Collectors.toList());

        // 设置高亮
        navbarList.parallelStream()
                .filter(m -> Ids.eq(m.getMenuId(), breadcrumbMenu.getMenuId()))
                .forEach(m -> m.setMenuActive(true));

        // 设置点击菜单时连接到第一个子菜单页面
        navbarList.parallelStream().forEach(m -> {
            Optional<Menu> firstSubMenu = authMenuList.parallelStream().filter(s -> Ids.eq(s.getMenuParentId(), m.getMenuId())).findFirst();
            if (firstSubMenu.isPresent()) {
                m.setMenuUrl(firstSubMenu.get().getMenuUrl());
            }
        });

        return navbarList;
    }

    /**
     * 获取左侧导航条列表
     *
     * @param authType 权限
     * @param pageId   页面ID
     * @return
     */
    @Override
    public List<Menu> selectSidebarList(Long authType, String pageId) {
        Assert.notNull(authType, "authType can not be null");
        Assert.notNull(pageId, "pageId can not be null");

        // 当前菜单
        Menu menu = selectMenuByPageId(pageId);
        final List<Menu> allMenuList = selectAll().stream()
                .filter(m -> m.getMenuShow())
                .filter(m -> (m.getMenuRoles() & authType) != 0)
                .collect(Collectors.toList());
        final Map<Long, Menu> allMenuMap = allMenuList.parallelStream()
                .collect(Collectors.toMap(Menu::getMenuId, Function.identity()));
        //设置高亮
        allMenuMap.get(menu.getMenuId()).setMenuActive(true);

        //构建左侧导航条
        return builderHierarchy(allMenuMap.get(menu.getMenuParentId()), allMenuList).getMenuList();
    }

    /**
     * 获取面包屑导航条列表
     *
     * @param authType 权限
     * @param pageId   页面ID
     * @return
     */
    @Override
    public List<Menu> selectBreadcrumbBarList(Long authType, String pageId) {
        Assert.notNull(authType, "authType can not be null");
        Assert.notNull(pageId, "pageId can not be null");

        //转Map方便下面根据MenuID获取，减少循环次数，顺便把权限个过滤了
        final Map<Long, Menu> allMenuMap = selectAll().parallelStream()
                .filter(m -> m.getMenuShow())
                .filter(m -> (m.getMenuRoles() & authType) != 0)
                .collect(Collectors.toMap(Menu::getMenuId, Function.identity()));

        //根据页面编号获取菜单信息
        Menu breadcrumbMenu = selectMenuByPageId(pageId);

        //面包屑列表
        final List<Menu> breadcrumbBarList = Lists.newArrayList(breadcrumbMenu);

        //死循环保险(循环次数不可能超过列表长度，保险起见设置为长度的2倍)。
        int loopSafety = allMenuMap.size() * 2;

        while (true) {

            // 找出我的亲爹
            breadcrumbMenu = allMenuMap.get(breadcrumbMenu.getMenuParentId());

            // 没爹的孩子滚粗
            if (breadcrumbMenu == null) {
                break;
            }

            // 写进族谱
            breadcrumbBarList.add(breadcrumbMenu);

            // 再往上就是猴子了，滚粗
            if (breadcrumbMenu.getMenuParentId().equals(TOP_LEVEL_NUM)) {
                break;
            }

            loopSafety--;

            // 我不会让你转到地球毁灭的
            if (loopSafety == 0) {
                throw new RuntimeException("生成面包屑列表异常，请检查菜单列表的父子关系。");
            }
        }

        //乾坤大挪移（人话：倒转）
        Collections.reverse(breadcrumbBarList);

        return breadcrumbBarList;
    }

    /**
     * 保存菜单树的层级结构与顺序
     *
     * @param menuList
     */
    @Override
    public void saveMenuTreeHierarchy(List<Menu> menuList) {
        Assert.notNull(menuList, "menuList can not be null");

        final Date modifiedTime = new Date();

        menuList.stream().forEach(m -> {

            //构建更新实体
            Menu entity = new Menu();
            entity.setMenuId(m.getMenuId());
            entity.setMenuParentId(m.getMenuParentId());
            entity.setRanking(m.getRanking());
            entity.setModifiedTime(modifiedTime);
            entity.setModifiedUid(m.getModifiedUid());

            //更新层级结构与顺序
            menuDAO.updateByPrimaryKeySelective(m);
        });
    }

    /**
     * 保存菜单信息
     *
     * @param menu
     */
    @Override
    public void saveMenu(Menu menu) {
        Assert.notNull(menu, "menu can not be null");
        Assert.notNull(menu.getPageId(), "pageId can not be null");

        //存在更新，不存在插入
        if (Ids.verifyId(menu.getMenuId())) {
            menuDAO.updateByPrimaryKeySelective(menu);
        } else {

            //验证PageID是否已经存在
            final Menu verifyMenu = selectMenuByPageId(menu.getPageId());
            if (verifyMenu != null) {
                String message = String.format("菜单编号已经存在，【%s：%s】", verifyMenu.getMenuName(), verifyMenu.getPageId());
                throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, CommonMessage.COMMON_MESSAGE, message);
            }

            menuDAO.insertSelective(menu);
        }
    }

    /**
     * 根据页面编号获取菜单信息
     *
     * @param pageId 页面ID
     * @return
     */
    public Menu selectMenuByPageId(String pageId) {
        Assert.notNull(pageId, "pageId can not be null");

        final Menu param = new Menu();
        param.setDelFlag(false);
        param.setPageId(pageId);
        return menuDAO.selectFirst(param);
    }

    /**
     * 构建菜单的子菜单列表
     *
     * @param m    当前菜单
     * @param list 菜单列表
     * @return
     */
    private Menu builderHierarchy(Menu m, List<Menu> list) {
        Assert.notNull(m, "m can not be null");
        Assert.notNull(list, "list can not be null");

        list.stream()
                .filter(i -> Ids.eq(i.getMenuParentId(), m.getMenuId()))
                .forEach(i -> {
                    if (m.getMenuList() == null) {
                        m.setMenuList(Lists.newLinkedList());
                    }
                    m.getMenuList().add(builderHierarchy(i, list));
                });
        return m;
    }

    /**
     * 获取所有的菜单（已删除的除外）
     *
     * @return
     */
    public List<Menu> selectAll() {

        final Menu param = new Menu();
        param.setDelFlag(false);

        //按照 RANKING 排序
        PageHelper.orderBy("ranking");
        return menuDAO.select(param);
    }

    /**
     * 寻找叶子节点
     *
     * @param parent 父节点
     * @param list   列表
     * @return
     */
    private Menu findLeafNode(Menu parent, List<Menu> list) {

        final Optional<Menu> leafNode = list.stream().filter(i -> Ids.eq(i.getMenuParentId(), parent.getMenuId())).findFirst();

        if (leafNode.isPresent()) {
            return findLeafNode(leafNode.get(), list);
        }

        return parent;
    }
}