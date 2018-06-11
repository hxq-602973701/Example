package com.java1234.service.sys.user;

import com.github.pagehelper.PageInfo;
import com.java1234.dal.entity.main.sys.user.User;
import com.java1234.dal.enums.DeviceTypeEnum;
import com.java1234.dal.vo.sys.UserVO;
import com.java1234.service.base.BaseService;

import java.util.List;

/**
 * UserService
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface UserService extends BaseService<User> {

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
    UserVO loginByAccount(String account, String pwd, String ip, String userAgent, DeviceTypeEnum deviceType);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    User selectUserById(Long userId);

    /**
     * 根据查询条件，以分页的方式获取用户列表
     *
     * @param param
     * @return
     */
    PageInfo<User> selectUserListByPaging(User param);

    /**
     * 验证用户账户、手机等是否存在
     *
     * @param param
     * @return
     */
    boolean selectUserDuplicate(User param);

    /**
     * 更新用户信息
     *
     * @param param
     * @return
     */
    User updateProfile(User param);

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    User saveUser(User user);

    /**
     * 批量逻辑删除用户
     *
     * @param userIds 要删除的用户集合
     * @param userId  当前登录用户
     * @return
     */
    void deleteUsers(Long[] userIds, Long userId);
}