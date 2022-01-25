package com.lzj.admin.controller;


import com.lzj.admin.exceptions.ParamsException;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.User;
import com.lzj.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zmw
 * @since 2022-01-24
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

//    @RequestMapping("/login")
//    @ResponseBody
//    public RespBean login(String userName, @RequestParam("password") String password, HttpSession session) {
//        //调用业务层。
//        try {
//            User user = userService.login(userName, password);
//            session.setAttribute("user", user);
//            //此时就表示账号和密码全都正确
//            return RespBean.success("用户登录成功~");
//        } catch (ParamsException e) {
//            e.printStackTrace();
//            return RespBean.error(e.getMsg());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return RespBean.error("用户登录失败!");
//        }
//    }


    //使用的是全局异常
    @RequestMapping("/login")
    @ResponseBody
    public RespBean login(String userName, String password, HttpSession session) {
        User user = userService.login(userName, password);
        session.setAttribute("user", user);
        //此时就表示账号和密码全都正确
        return RespBean.success("用户登录成功~");
    }

    @RequestMapping("/setting")
    public String setting(HttpSession session) {
        //如果直接进行页面的跳转的话，刷新当前页面之后，更新的信息无法办法在当前的页面中显示最小的信息
        //从session中取出当前的用户
        User user = (User) session.getAttribute("user");
        //根据当前用户的id查询数据更新书的数据，然后在跳转到响应的页面，
        session.setAttribute("user", userService.getById(user.getId()));
        return "user/setting";
    }

    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public RespBean updateUserInfo(User user) {
        try {
            userService.updateUserInfo(user);
            return RespBean.success("信息修改成功");
        } catch (ParamsException e) {
            e.printStackTrace();
            return RespBean.error(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("信息更新失败!");
        }
    }


    //密码修改页面的跳转
    @RequestMapping("/password")
    public String updatePassword() {
        return "/user/password";
    }

    @RequestMapping("/updateUserPassword")
    @ResponseBody
    public RespBean updateUserPassword(HttpSession session, String oldPassword, String newPassword, String confirmPassword) {
        try {
            User user = (User) session.getAttribute("user");
            userService.updatePassword(user.getUserName(), oldPassword, newPassword, confirmPassword);
            return RespBean.success("修改密码成功！");
        } catch (ParamsException e) {
            e.printStackTrace();
            return RespBean.error(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.success("修改密码失败！");
        }
    }

}
