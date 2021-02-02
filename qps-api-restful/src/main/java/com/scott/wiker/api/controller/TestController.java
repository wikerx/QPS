package com.scott.wiker.api.controller;

import com.alibaba.fastjson.JSON;
import com.scott.wiker.entity.test.Users;
import com.scott.wiker.service.test.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :TestController
 * @description : 无限制式访问接口
 * @data :2020/9/7 0007 下午 2:17
 * @status : 编写
 **/

@Api("数据库测试接口管理")
@RestController
@RequestMapping(value = "/rest", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class TestController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询数据库数据")
    @RequestMapping(value = "test")
    public Object test(){
        List<Users> list = userService.selectListUsers();
        log.info("list:{}", JSON.toJSONString(list));
        return list;
    }
}
