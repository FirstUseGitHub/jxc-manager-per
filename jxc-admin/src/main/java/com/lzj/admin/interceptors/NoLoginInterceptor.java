package com.lzj.admin.interceptors;

import com.lzj.admin.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 张明伟
 * @version 1.0
 **/
public class NoLoginInterceptor implements HandlerInterceptor {
    //进入controller之前执行，返回的Boolean值决定是否执行后续操作
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            response.sendRedirect("index");
            return false;
        }
        return true;
    }
}
