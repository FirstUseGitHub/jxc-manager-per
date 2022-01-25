package com.lzj.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author 张明伟
 * @version 1.0
 **/
@Controller
public class MainController {

    //主页面
    @RequestMapping("main")
    public String main() {
        return "main";
    }

    //系统首页
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    //欢迎页
    @RequestMapping("welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/signout")
    public String signout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/index";
    }

}
