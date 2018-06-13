package com.java1234.web.controller.system;

import com.java1234.dal.annotation.AuthCheck;
import com.java1234.dal.constant.page.SystemPageID;
import com.java1234.dal.entity.main.sys.config.Config;
import com.java1234.dal.entity.main.sys.dept.Dept;
import com.java1234.dal.entity.main.sys.role.Role;
import com.java1234.dal.entity.main.sys.user.User;
import com.java1234.dal.enums.DeptTypeEnum;
import com.java1234.dal.security.login.MD5;
import com.java1234.dal.utils.CookieUtil;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.utils.LoginContext;
import com.java1234.dal.vo.common.ResultVO;
import com.java1234.exception.DataErrorException;
import com.java1234.exception.message.UserMessage;
import com.java1234.service.sys.dept.DeptService;
import com.java1234.service.sys.role.RoleService;
import com.java1234.service.sys.token.TokenService;
import com.java1234.service.sys.user.UserService;
import com.java1234.util.CommonMessage;
import com.java1234.util.RequestUtil;
import com.java1234.util.ResponseCode;
import com.java1234.web.base.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/6/8.
 */
@Controller
public class UserController extends BaseController {

    /**
     * 用户Service
     */
    @Resource
    private UserService userService;

    /**
     * 单位Service
     */
    @Resource
    private DeptService deptService;

    /**
     * 角色Service
     */
    @Resource
    private RoleService roleService;

    /**
     * 令牌Service
     */
    @Resource
    private TokenService tokenService;

    /**
     * 用户管理页面
     *
     * @param model
     * @return
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/system/user-list", method = RequestMethod.GET)
    public String userListView(final Model model) {
        return "/system/user/user-list";
    }

    /**
     * 分页获取用户列表
     *
     * @param model
     * @param param
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/system/user-page", method = RequestMethod.GET)
    public void userPageApi(final Model model, final User param) {
        DataPipe.in(model).response(userService.selectUserListByPaging(param));
    }

    /**
     * 显示用户编辑页面
     *
     * @param model
     * @param userId
     * @param branchId
     * @return
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/system/user", method = RequestMethod.GET)
    public String userDetailView(final Model model, Long userId, Integer deptId, Integer branchId) {
        if (userId != null && deptId == null) {
            deptId = userService.selectUserById(userId).getDeptId();
        }
        Dept dept = deptService.selectDeptById(deptId);
        // 如果用户部门ID不存在，获取当前登录用于的部门ID
        if (dept == null) {
            deptId = userService.selectUserById(LoginContext.getUser().getUserId()).getDeptId();
            dept = deptService.selectDeptById(deptId);
        }

        // 用户信息
        final User user = userService.selectUserById(userId);

        //判断用户是否有超级管理员权限
        boolean flag = (LoginContext.getUser().getAuthType() & 1) > 0 ? true : false;
        model.addAttribute("flag", flag);

        if (user != null) {
            deptId = user.getDeptId();
        }
        dept = deptService.selectTopDept(dept);
        List<Dept> deptList = deptService.selectSubList(dept.getDeptId(), false).stream()
                .filter(d -> (d.getDeptType() & 128) == 0)
                .filter(x -> (x.getDeptType() & 1024) == 0)
                .collect(Collectors.toList());
        if (dept.getDeptType() == DeptTypeEnum.CITY_BUREAU.getValue()) {
            //筛选市局下属单位
            for (int i = 0; i < deptList.size(); i++) {
                if (!deptList.get(i).getDeptParentId().equals(dept.getDeptId())) {
                    deptList.remove(i);
                    i -= 1;
                }
            }
        }

        //角色列表
        final List<Role> roleList = roleService.selectAll();

        model.addAttribute("user", user);
        model.addAttribute("deptList", deptList);
        model.addAttribute("roleList", roleList);
        if (deptId.equals(branchId)) {
            deptId = null;
        }
        model.addAttribute("deptId", deptId);
        //根据传递的分局ID，显示分局名
        Dept branch = deptService.selectDeptById(branchId);
        model.addAttribute("branchId", branch.getDeptId());
        model.addAttribute("branchName", branch.getDeptName());

        return "/system/user/user-edit";
    }

    /**
     * 保存用户
     *
     * @param model
     * @param param
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/system/user", method = RequestMethod.POST)
    public void saveUserApi(final Model model, final User param) {

        Long uid = LoginContext.getUserId();

        User user = new User();

        //如果是分县局添加用户，设置部门Id、分支Id
        if (param.getDeptId() == null) {
            param.setDeptId(param.getBranchId());
            param.setBranchId(2);
        }

        //判断传递的branchId是否为deptId的上级分局，如果是则直接保存，否则需查找上级分局
        if (param.getBranchId() == deptService.selectDeptById(param.getDeptId()).getDeptParentId()) {
            user.setBranchId(param.getBranchId());
        } else {
            Dept newDept = deptService.selectDeptById(param.getDeptId());
            do {
                newDept = deptService.selectDeptById(newDept.getDeptParentId());
                if (newDept != null && ((newDept.getDeptType() & 128) > 0 || (newDept.getDeptType() & 1024) > 0)) {
                    user.setBranchId(newDept.getDeptId());
                    break;
                } else if (newDept == null) {
                    user.setBranchId(2);
                    break;
                }
            }
            while (true);
        }

        user.setUserId(param.getUserId());
        user.setAccount(param.getAccount());
        user.setUserName(param.getUserName());
        user.setDeptId(param.getDeptId());
        user.setIdCardNum(param.getIdCardNum());
        user.setPhone(param.getPhone());
        user.setAuthType(param.getAuthType());
        user.setLoginWay(param.getLoginWay());
        user.setPwd(param.getPwd());
        user.setRegIp(RequestUtil.getRemoteIp());
        user.setLastIp(RequestUtil.getRemoteIp());
        user.setCreateUid(uid);
        user.setModifiedUid(uid);

        if (param.getUserId() != null) {
            userService.updateProfile(user);
        } else {
            userService.saveUser(user);
        }
    }

    /**
     * 逻辑删除用户
     *
     * @param model
     * @param policeIds 要删除的用户集合
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/system/user", method = RequestMethod.DELETE)
    public void delUserApi(final Model model, Long[] policeIds) {

        Long userId = LoginContext.getUserId();
        userService.deleteUsers(policeIds, userId);
    }

    /**
     * 重置密码
     *
     * @param model
     * @param user
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/system/reset-password", method = RequestMethod.POST)
    public void resetUserPassword(final Model model, User user) {
        String pwd = com.java1234.util.Config.getString("user.default.password");
        user.setPwd(MD5.md5(pwd));
        user.setModifiedUid(LoginContext.getUserId());
        user.setModifiedTime(new Date());
        userService.updateByPrimaryKeySelective(user);
    }

    /**
     * 用户账户、手机等是否存在接口
     *
     * @param model model
     * @param param 用户信息
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/user/verify", method = RequestMethod.GET)
    public void verifyUserApi(final Model model, final User param) {

        // 参数验证
        if (param == null) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, UserMessage.ACCOUNT_REQUIRED);
        }

        // 判读用户账户、手机等是否重复
        boolean duplicate = userService.selectUserDuplicate(param);

        ResultVO resultVO = new ResultVO();
        resultVO.setExist(duplicate);

        // 返回给客户端
        DataPipe.in(model).response(resultVO);
    }

    /**
     * 用户登出接口
     */
    @AuthCheck(page = SystemPageID.LOGOUT)
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logoutView(Model model) {

        // 登出
        tokenService.invalidToken(LoginContext.getToken());

        // 删除会话Token
        DataPipe.in(model).deleteCookie(CookieUtil.SESSION_ID);

        //跳转到主页
        return redirectTo("/login");
    }

    /**
     * 我的基本信息页面
     *
     * @return
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String showProfileView(final Model model) {

        // 用户信息
        User user = LoginContext.getUser();
        //单位列表...过滤分局单位不显示
        //（如果是市局的话，不显示分局县局单位；如果是分局县局及以下的话，本身查找的List就不存在分局县局单位，所以直接过滤分局县局单位）
        final List<Dept> deptList = deptService.selectSubList(user.getBranchId(), true).stream()
                .filter(d -> (d.getDeptType() & 128) == 0)
                .filter(x -> (x.getDeptType() & 1024) == 0)
                .collect(Collectors.toList());
        //角色列表
        final List<Role> roleList = roleService.selectAll();

        model.addAttribute("deptId", user.getDeptId());
        model.addAttribute("user", user);
        model.addAttribute("deptList", deptList);
        model.addAttribute("roleList", roleList);

        Dept dept = deptService.selectDeptById(user.getDeptId());
        //无下级单位直接显示所属单位名称
        if (deptList.size() == 0 || null == deptList) {
            model.addAttribute("deptName", dept.getDeptName());
        }

        //个人信息查看
        String type = "view";
        //根据传递的分局ID，显示分局名
        Dept branch = deptService.selectDeptById(user.getBranchId());
        model.addAttribute("branchId", branch.getDeptId());
        model.addAttribute("branchName", branch.getDeptName());
        model.addAttribute("type", type);
        model.addAttribute("flag", true);
        return "/system/user/user-edit";
    }

    /**
     * 修改密码页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/pwd", method = RequestMethod.GET)
    public String changePwdView(final Model model) {
        return "/system/user/user-password";
    }

    /**
     * 修改密码接口
     *
     * @param model
     * @param oldPwd
     * @param newPwd
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/user/password/alter", method = RequestMethod.POST)
    public void alterPassword(final Model model, final String oldPwd, final String newPwd) {

        if (StringUtils.isBlank(oldPwd)) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, UserMessage.PASSWORD_REQUIRED);
        }

        if (StringUtils.isBlank(newPwd)) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, UserMessage.PASSWORD_REQUIRED);
        }

        if (oldPwd.equals(newPwd)) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, CommonMessage.PASSWORD_EQUAL);
        }

        // 更新用户密码
        userService.alterPassword(LoginContext.getUserId(), oldPwd, newPwd);

    }

    /**
     * 重置密码
     */
    @AuthCheck(page = SystemPageID.USER_LIST)
    @RequestMapping(value = "/user/password/reset", method = RequestMethod.POST)
    public void resetPassword(Long uid) {
        userService.resetPassword(uid);
    }
}
