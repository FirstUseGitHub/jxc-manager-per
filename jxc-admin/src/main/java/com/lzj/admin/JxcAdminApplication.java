package com.lzj.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author 张明伟
 * @version 1.0
 **/
@SpringBootApplication
//指定要变成实现类的接口所在的包，然后包下面的所有接口在编译之后都会创建相应的实现类
@MapperScan("com.lzj.admin.mapper")
public class JxcAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(JxcAdminApplication.class, args);
    }
}
