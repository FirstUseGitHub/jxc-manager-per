package com.lzj.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.pojo.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zmw
 * @since 2022-01-24
 */
public interface IUserService extends IService<User> {

    //用户登录
    User login(String userName, String password);

    //查询用户名是否在数据库中存在
    public User findUserByUserName(String usenName);

    void updateUserInfo(User user);

    void updatePassword(String userName, String oldPpassword, String newPassword, String confirmPassword);
}
