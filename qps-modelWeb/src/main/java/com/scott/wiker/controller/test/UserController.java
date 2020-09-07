package com.scott.wiker.controller.test;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.scott.wiker.entity.test.Users;
import com.scott.wiker.service.test.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :UserController
 * @description :
 * @data :2020/9/7 0007 上午 10:36
 * @status : 编写
 **/
@RestController
@RequestMapping(value = "/rest", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "test")
    public Object test(){
        List<Users> list = userService.selectListUsers();
        log.info("userList:{}", JSON.toJSONString(list));
        return list;
    }


    public static void main(String[] args) {
        // 最简单的HTTP请求，可以自动通过header等信息判断编码，不区分HTTP和HTTPS
        String result = HttpUtil.get("http://localhost:8080/dataSource/rest/test");
        log.info(result);
    }
}
