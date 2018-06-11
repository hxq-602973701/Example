package com.java1234.dal.mapper.main.sys.user;

import com.java1234.dal.entity.main.sys.user.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

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
     * @return 用户信息
     */
    int incrLoginCount(@Param("userId") Long userId, @Param("lastIp") String lastIp);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    User selectUserById(Long userId);

    /**
     * 根据条件查询用户列表
     *
     * @param param
     * @return
     */
    List<User> selectUserListByParam(User param);

    /**
     * 验证手机号、身份证号是否存在
     *
     * @param param
     * @return
     */
    int selectUserDuplicate(User param);
}