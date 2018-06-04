package com.java1234.service.sys.user.impl;

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
import com.java1234.service.sys.token.TokenService;
import com.java1234.service.sys.user.UserService;

import javax.annotation.Resource;

import com.java1234.util.ResponseCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
     * 令牌Service
     */
    @Resource
    private TokenService tokenService;

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
     * 初始化用户基本信息
     *
     * @param user
     * @param dept
     * @return
     */
    private User initProfile(User user, Dept dept) {
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