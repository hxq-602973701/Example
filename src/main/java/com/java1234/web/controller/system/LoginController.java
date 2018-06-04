package com.java1234.web.controller.system;

import com.java1234.dal.entity.main.sys.menu.Menu;
import com.java1234.dal.enums.DeviceTypeEnum;
import com.java1234.dal.utils.CookieUtil;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.vo.sys.UserVO;
import com.java1234.service.sys.menu.MenuService;
import com.java1234.service.sys.user.UserService;
import com.java1234.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * 系统登录Controller
 */
@Controller
public class LoginController {


    /**
     * 用户Service
     */
    @Resource
    private UserService userService;

    /**
     * 菜单Service
     */
    @Resource
    private MenuService menuService;

    /**
     * 系统登录页
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginView(final Model model) {

        return "login";
    }

    /**
     * 用户登录接口
     *
     * @param model
     * @param account  用户账户
     * @param password 密码
     * @param remember 是否记住账号
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void loginApi(final Model model, final String account, final String password, final boolean remember, DeviceTypeEnum deviceType) {

        if (deviceType == null) {
            deviceType = DeviceTypeEnum.WEB;
        }

        // 登录
        final UserVO userVO = userService.loginByAccount(account, password, RequestUtil.getRemoteIp(), RequestUtil.getUserAgent(), deviceType);

//        Dept dept = deptService.selectByPrimaryKey(userVO.getUser().getDeptId());
//        userVO.getUser().setDept(dept);
//
//        //权限
        final Long authType = userVO.getUser().getAuthType();
        final Menu menu = menuService.selectFirstAuthMenu(authType);
        userVO.setRedirectUrl(menu.getMenuUrl());

        // 返回给客户端(并保存token到cookie)
        DataPipe dataPipe = DataPipe
                .in(model)
                .response(userVO)
                .addCookie(CookieUtil.getSessionCookie(userVO.getToken()));

        //是否记住账号
        if (remember == true) {
            dataPipe.addCookie("account", account, 60 * 60 * 24 * 365);
        } else {
            dataPipe.deleteCookie("account");
        }
    }


}
