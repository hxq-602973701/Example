package com.java1234.service.sys.user.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.sys.user.UserDAO;
import com.java1234.dal.entity.main.sys.dept.Dept;
import com.java1234.dal.entity.main.sys.token.Token;
import com.java1234.dal.entity.main.sys.user.User;
import com.java1234.dal.enums.DeviceTypeEnum;
import com.java1234.dal.enums.LoginExceptionEnum;
import com.java1234.dal.security.login.MD5;
import com.java1234.dal.vo.sys.UserVO;
import com.java1234.exception.DataErrorException;
import com.java1234.exception.message.UserMessage;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.sys.dept.DeptService;
import com.java1234.service.sys.token.TokenService;
import com.java1234.service.sys.user.UserService;
import com.java1234.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * UserService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    /**
     * 系统用户Dao
     */
    @Resource
    private UserDAO userDAO;

    /**
     * 系统单位Service
     */
    @Resource
    private DeptService deptService;

    /**
     * 令牌Service
     */
    @Resource
    private TokenService tokenService;

    /**
     * 默认头像
     */
    private static final String DEFAULT_AVATAR = Config.getString("config.default_avatar");

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<User> getDAO() {
        return userDAO;
    }

    /**
     * 用户登录
     *
     * @param account    用户账户
     * @param pwd        用户密码
     * @param ip         登录IP地址
     * @param userAgent  客户端信息
     * @param deviceType 设备类型
     * @return
     */
    @Override
    public UserVO loginByAccount(String account, String pwd, String ip, String userAgent, DeviceTypeEnum deviceType) {

        //非空验证
        Assert.notNull(account, "account can not be null");
        Assert.notNull(pwd, "pwd can not be null");
        Assert.notNull(ip, "ip can not be null");
        Assert.notNull(userAgent, "userAgent can not be null");
        Assert.notNull(deviceType, "deviceType can not be null");

        final User user = selectUserByAccount(account);

        // 用户账户不存在
        if (user == null) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, UserMessage.ACCOUNT_NOT_EXIST);
        }

        //账号无登录权限
        if ((user.getLoginWay() & 1) != 1) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, LoginExceptionEnum.ACCOUNT_NOT_AUTH_LOGIN);
        }

        // 密码校验
        if (!user.getPwd().equals(MD5.md5(pwd))) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, UserMessage.PASSWORD_MISS);
        }

        // 构建Token
        Token token = tokenService.getToken(user.getUserId(), deviceType);

        // 更新登录次数
        userDAO.incrLoginCount(user.getUserId(), ip);

        return new UserVO(token.getToken(), initProfile(user, null));
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public User selectUserById(Long userId) {
        return userDAO.selectUserById(userId);
    }

    /**
     * 根据查询条件，以分页的方式获取用户列表
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo<User> selectUserListByPaging(final User param) {

        final Map<Integer, Dept> deptMap = deptService.selectAll().parallelStream().collect(Collectors.toMap(Dept::getDeptId, Function.identity()));

        // 获取分页数据
        PageInfo<User> pageInfo = userDAO.selectUserListByPaging(param);

        List<User> profileList = Lists.newArrayList();
        pageInfo.getList().forEach(
                (user) -> profileList.add(initProfile(user, deptMap.get(user.getDeptId())))
        );
        pageInfo.getList().clear();
        pageInfo.getList().addAll(profileList);

        return pageInfo;
    }

    /**
     * 验证用户账户、手机等是否存在
     *
     * @param param
     * @return
     */
    @Override
    public boolean selectUserDuplicate(User param) {
        Assert.notNull(param, "param can not be null");

        return userDAO.selectUserDuplicate(param);
    }

    /**
     * 更新用户信息
     *
     * @param param
     */
    @Override
    public User updateProfile(User param) {
        Assert.notNull(param, "param can not be null");
        Assert.notNull(param.getUserId(), "userId can not be null");

        // 修改密码
        if (StringUtils.isNotBlank(param.getPwd())) {
            param.setPwd(MD5.md5(param.getPwd().trim()));
        }

        userDAO.updateByPrimaryKeySelective(param);

        return initProfile(userDAO.selectUserById(param.getUserId()), null);
    }

    /**
     * 保存用户
     *
     * @param param
     * @return
     */
    @Override
    public User saveUser(User param) {
        Assert.notNull(param, "param can not be null");

        final Long userId = param.getUserId();

        final User user;
        if (Ids.verifyId(userId)) {
            user = updateProfile(param);
        } else {
            user = insertUser(param);
        }

        return user;
    }

    /**
     * 批量逻辑删除用户,当传进来的用户数组下都无信息员的时候，则删除成功
     * 有信息员的则返回有信息员列表
     *
     * @param userIds 要删除的用户集合
     * @param userId  当前登录用户
     */
    @Override
    public void deleteUsers(Long[] userIds, Long userId) {
        Assert.notNull(userIds, "policeIds not be null");

        //如果所选的用户都没有信息员，则进行删除
        userDAO.deleteWithLogicByPrimaryKeys(userIds, userId);
    }

    /**
     * 修改用户密码
     *
     * @param userId 用户ID
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     */
    @Override
    public void alterPassword(Long userId, String oldPwd, String newPwd) {
        Assert.notNull(userId, "userId can not be null");
        Assert.notNull(oldPwd, "oldPwd can not be null");
        Assert.notNull(newPwd, "newPwd can not be null");

        // 获取用户
        final User user = userDAO.selectUserById(userId);

        // 旧密码校验
        if (!user.getPwd().equals(MD5.md5(oldPwd))) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, UserMessage.PASSWORD_MISS);
        }
        //新密码不能与旧密码相同
        if (user.getPwd().equals(MD5.md5(newPwd))) {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, UserMessage.PASSWORD_MISS);
        }

        User resetPwdUser = new User();
        resetPwdUser.setUserId(user.getUserId());
        resetPwdUser.setPwd(MD5.md5(newPwd));

        // 更新密码
        userDAO.updateByPrimaryKeySelective(resetPwdUser);
    }

    /**
     * 重置密码
     *
     * @param userId
     */
    @Override
    public void resetPassword(Long userId) {
        Assert.notNull(userId, "userId can not be null");

        User resetPwdUser = new User();
        resetPwdUser.setUserId(userId);
        resetPwdUser.setPwd(MD5.md5(Config.getString("user.default.password")));
        userDAO.updateByPrimaryKeySelective(resetPwdUser);
    }

    /**
     * 添加用户
     */
    public User insertUser(User param) {
        Assert.notNull(param, "param can not be null");
        Assert.notNull(param.getAccount(), "account can not be null");
        Assert.notNull(param.getUserName(), "userName can not be null");
        Assert.notNull(param.getAuthType(), "authType can not be null");
        Assert.notNull(param.getDeptId(), "deptId can not be null");

        // 用户账户
        final String account = StringUtils.trim(param.getAccount());
        if (StringUtils.isNotBlank(account)) {
            // 检查用户账户在用户表中是否存在
            final User userNick = userDAO.selectUserByAccount(account);
            if (userNick != null) {
                throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, UserMessage.ACCOUNT_REPEAT);
            }
            param.setAccount(account);

        } else {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, CommonMessage.REQUEST_PARAM_REQUIRED, "用户账户");
        }

        // 用户姓名
        final String userName = StringUtils.trim(param.getUserName());
        if (StringUtils.isNotBlank(userName)) {
            param.setUserName(userName);
        } else {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, CommonMessage.REQUEST_PARAM_REQUIRED, "用户姓名");
        }

        // 手机号码
        final String phone = StringUtils.trim(param.getPhone());
        if (StringUtil.isMobileNumber(phone)) {
            param.setPhone(phone);
        } else {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, CommonMessage.ILLEGAL_PARAM_REQUIRED, "手机号码");
        }


        // 用户头像(为空则使用默认头像)
        final String avatar = StringUtils.trim(StringUtils.defaultIfBlank(param.getAvatar(), DEFAULT_AVATAR));
        param.setAvatar(avatar);

        // 个性签名(允许设置为空)
        if (param.getSignature() != null) {
            param.setSignature(param.getSignature().trim());
        }

        // 所属单位
        if (param.getDeptId() != null) {
            param.setDeptId(param.getDeptId());
        } else {
            throw new DataErrorException(ResponseCode.SC_BAD_REQUEST, CommonMessage.REQUEST_PARAM_REQUIRED, "所属单位");
        }

        // 用户密码(为空则使用默认密码：123456)
        final String pwd = StringUtils.trim(StringUtils.defaultIfBlank(param.getPwd(), "123456"));
        param.setPwd(MD5.md5(pwd));

        // 设备类型
        param.setDeviceType(DeviceTypeEnum.WIN_DESK_TOP.getValue());
        param.setLoginSum(0L);

        userDAO.insertSelective(param);

        return initProfile(userDAO.selectUserById(param.getUserId()), null);
    }

    /**
     * 初始化用户基本信息
     *
     * @param user
     * @param dept
     * @return
     */
    public static User initProfile(User user, Dept dept) {
        if (user == null) {
            return null;
        }

        User resultUser = new User();
        resultUser.setUserId(user.getUserId());
        resultUser.setAccount(user.getAccount());
        resultUser.setUserName(user.getUserName());
        resultUser.setPhone(user.getPhone());
        resultUser.setEmail(user.getEmail());
        resultUser.setAvatar(user.getAvatar());
        resultUser.setSignature(StringUtils.defaultString(user.getSignature(), "这个人很懒,什么都没留下"));
        resultUser.setAuthType(user.getAuthType());
        resultUser.setDeptId(user.getDeptId());
        resultUser.setIdCardNum(user.getIdCardNum());
        resultUser.setLastIp(user.getLastIp());
        resultUser.setLoginSum(user.getLoginSum());
        resultUser.setCreateTime(user.getCreateTime());
        resultUser.setLoginWay(user.getLoginWay());
        resultUser.setDept(dept);
        resultUser.setBranchId(user.getBranchId());
        return resultUser;
    }

    /**
     * 根据账号查询账户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    private User selectUserByAccount(String account) {
        return userDAO.selectUserByAccount(account);
    }
}