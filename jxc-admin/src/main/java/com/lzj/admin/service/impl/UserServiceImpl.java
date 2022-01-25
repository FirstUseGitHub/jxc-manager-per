package com.lzj.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.mapper.UserMapper;
import com.lzj.admin.pojo.User;
import com.lzj.admin.service.IUserService;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.StringUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zmw
 * @since 2022-01-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User login(String userName, String password) {
        //先对用户名进行非空的校验
        //StringUtil.isNotEmpty(userName)不为空返回true。为空才返回false，在取反，然后断言成功，在设置msg
        AssertUtil.isTrue(StringUtil.isEmpty(userName), "用户名不能为空");

        //对密码进行非空的检验
        AssertUtil.isTrue(StringUtil.isEmpty(password), "密码不能为空");

        //从数据库中查询该用户是否存在。
        User user = findUserByUserName(userName);
        //对查到的user再次进行判断
        AssertUtil.isTrue(null == user, "该用户记录不存在或已注销!");

        //在判断密码是否正确
        AssertUtil.isTrue(!(user.getPassword().equals(password)), "密码错误!");
        //此时表示该用户是合法的用户
        return user;
    }

    //根据userName查询该用户
    @Override
    public User findUserByUserName(String usenName) {
        //t_user表中有一个字段is_del字段。该字段的值为0表示该用户还存在。如果值为1就表示该用户已经被删除了.
        //用户存在需要满足两个条件
        //1.is_del = 0
        //2.user_name字段的值和用户输入的值一样
        return this.baseMapper.selectOne(new QueryWrapper<User>().eq("is_del", 0).eq("user_name", usenName));
    }

    //用户信息修改
    @Override
    public void updateUserInfo(User user) {
        //用户信息修改前需要先进行以下校验
        //检验的条件为
        //用户名不能为null
        AssertUtil.isTrue(StringUtil.isEmpty(user.getUserName()), "用户名不能为空");
        //用户名不能重复
        //根据前端传送过来的userName从数据库中进行查询。
        User temp = findUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "用户名已存在!");//如果使用这个条件那么可以不修改用户名，修改其他信息
        // AssertUtil.isTrue(null != temp, "用户名已存在!");
        //此时表示，要修改的这个用户名没有问题，可以进行修改。
        AssertUtil.isTrue(!(this.updateById(user)), "用户信息更新失败!");
    }

    @Override
    public void updatePassword(String userName, String oldPpassword, String newPassword, String confirmPassword) {
        User user = null;
        //根据用户从，数据库中查询出当前的用户的信息
        user = findUserByUserName(userName);
        //进行非空的校验
        AssertUtil.isTrue(null == user, "用户不存在或未登录~~");
        AssertUtil.isTrue(StringUtil.isEmpty(oldPpassword), "旧密码不能为空~");
        AssertUtil.isTrue(StringUtil.isEmpty(newPassword), "新密码不能为空~");
        AssertUtil.isTrue(StringUtil.isEmpty(confirmPassword), "确认不能为空~");
        //校验旧密码是否正确
        AssertUtil.isTrue(!(user.getPassword().equals(oldPpassword)), "旧密码不正确");
        //校验两次输入的密码是否一样
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)), "第二次密码和第一次的密码不正确");
        AssertUtil.isTrue(newPassword.equals(oldPpassword), "新密码和旧密码不能一致~");
        user.setPassword(newPassword);
        AssertUtil.isTrue(!(this.updateById(user)), "用户密码更新失败~");
    }
}
