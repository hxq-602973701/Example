package com.java1234.util;

import com.java1234.dal.entity.main.book.Book;
import com.java1234.dal.entity.main.sys.menu.Menu;
import com.java1234.dal.entity.main.sys.user.User;
import com.java1234.dal.utils.LoginContext;
import com.java1234.service.book.BookService;
import com.java1234.service.sys.menu.MenuService;
import com.java1234.service.sys.user.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Freemarker工具类
 *
 * @author lt 2017-9-1 晚上21:54:52
 * @version 1.0.0
 */

@Component
public class FreemarkerUtil {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

    /**
     * 全局配置
     *
     * @return
     */
    public static Book getBook() {
        BookService bookService = SpringUtil.getBean(BookService.class);
        Book book = new Book();
        book.setBookId(4L);
        return bookService.selectOne(book);
    }

    /**
     * 获取模块化的子系统列表
     *
     * @return
     */
    public static List<Menu> subSystem() {
        final MenuService menuService = SpringUtils.getBean(MenuService.class);
        return menuService.selectSubSystem(LoginContext.getAuthType());
    }

    /**
     * 获取头部导航条
     *
     * @param pageId 页面ID
     * @return
     */
    public static List<Menu> navbar(String pageId) {
        Assert.notNull(pageId, "pageId can not be null");

        final MenuService menuService = SpringUtil.getBean(MenuService.class);
        return menuService.selectNavbarList(LoginContext.getAuthType(), pageId);
    }

    /**
     * 获取左侧导航条
     *
     * @param pageId 页面ID
     * @return
     */
    public static List<Menu> sidebar(String pageId) {
        Assert.notNull(pageId, "pageId can not be null");

        final MenuService menuService = SpringUtil.getBean(MenuService.class);
        return menuService.selectSidebarList(LoginContext.getAuthType(), pageId);
    }

    /**
     * 面包屑导航条
     *
     * @param pageId 页面ID
     * @return
     */
    public static List<Menu> breadcrumbBar(String pageId) {
        Assert.notNull(pageId, "pageId can not be null");

        final MenuService menuService = SpringUtil.getBean(MenuService.class);
        return menuService.selectBreadcrumbBarList(LoginContext.getAuthType(), pageId);
    }

    /**
     * 检查是否具有权限
     *
     * @param role
     * @param authType
     * @return
     */
    public static boolean isAuthCheck(Long role, Long authType) {
        if (role == null || authType == null) {
            return false;
        }
        return (role & authType) != 0;
    }

    /**
     * 获取用户信息(必须是登录状态下调用)
     *
     * @return
     */
    public static User getUserProfile() {
        return UserServiceImpl.initProfile(LoginContext.getUser(), null);
    }
}
