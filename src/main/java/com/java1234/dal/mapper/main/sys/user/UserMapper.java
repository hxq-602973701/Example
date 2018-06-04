package com.java1234.dal.mapper.main.sys.user;

import com.java1234.dal.entity.main.sys.user.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    /**
     * 根据用户账号获取用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    User selectUserByAccount(String account);

    /**
     * 更新登陆次数
     *
     * @param userId 用户id
     * @param lastIp 最后登录的ip
     */
    int incrLoginCount(@Param("userId") Long userId, @Param("lastIp") String lastIp);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    User selectUserById(Long userId);
}