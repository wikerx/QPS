package com.scott.wiker.api.onlinepay;

import com.alibaba.fastjson.JSON;
import com.scott.wiker.apiversion.ApiVersion;
import com.scott.wiker.entity.test.Users;
import com.scott.wiker.service.test.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :Version1
 * @description : 版本式接口访问
 * @data :2020/9/23 0023 下午 4:58
 * @status : 编写
 **/
@Slf4j
@ApiVersion(2)
@RequestMapping(value = "online/{version}")
@RestController
public class Version2Controller {

    @Autowired
    private UserService userService;


    @PostMapping(value = "test")
    public Object test(){
        log.info("这是version = 2");
        List<Users> list = userService.selectListUsersSlave();
        log.info("备节点数据：{}", JSON.toJSONString(list));
        return "version2";
    }

}
