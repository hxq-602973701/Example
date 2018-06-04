package com.java1234.dal.dao.sys.user;

import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.entity.main.sys.user.User;

/**
 * UserDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface UserDAO extends BaseDAO<User> {
    /**
     * 根据账号获取用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    User selectUserByAccount(String account);

    /**
     * 更新登陆次数
     *
     * @param userId 用户id
     * @param lastIp 最后登录ip
     */
    int incrLoginCount(Long userId, String lastIp);

    /**
     * 根据用户id获取用户基本信息
     *
     * @param userId
     * @return
     */
    User selectUserById(Long userId);
}