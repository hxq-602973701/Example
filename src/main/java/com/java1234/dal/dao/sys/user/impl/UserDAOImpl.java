package com.java1234.dal.dao.sys.user.impl;

import com.java1234.dal.dao.base.impl.BaseDAOImpl;
import com.java1234.dal.dao.sys.user.UserDAO;
import com.java1234.dal.entity.main.sys.user.User;
import com.java1234.dal.mapper.main.sys.user.UserMapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tk.mybatis.mapper.common.Mapper;

/**
 * UserDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {

    /**
     * UserMapper
     */
    @Resource
    private UserMapper userMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<User> getMapper() {
        return userMapper;
    }

    /**
     * 根据账号查询用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    @Override
    public User selectUserByAccount(String account) {

        if (account == null) {
            return null;
        }

        return userMapper.selectUserByAccount(account);
    }

    /**
     * 更新登陆次数
     *
     * @param uid    用户id
     * @param lastIp 最后登录ip
     */
    @Override
    public int incrLoginCount(Long uid, String lastIp) {
        Assert.notNull(uid, "uid can not be null");

        // 更新登录次数
        return userMapper.incrLoginCount(uid, lastIp);
    }

    /**
     * 根据用户UID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public User selectUserById(Long userId) {

        if (userId == null) {
            return null;
        }

        return userMapper.selectUserById(userId);
    }
}