package com.scott.wiker.api.onlinepay;

import com.alibaba.fastjson.JSON;
import com.scott.wiker.apiversion.ApiVersion;
import com.scott.wiker.auth.AuthRequired;
import com.scott.wiker.entity.test.Users;
import com.scott.wiker.restrictor.AnRateLimit;
import com.scott.wiker.service.test.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api("接口版本管理-v1")
@ApiVersion(1)
@RequestMapping(value = "online/{version}")
@RestController
public class Version1Controller {

    @Autowired
    private UserService userService;

    /**
     * permitsPerSecond=N 规定时间内的交易笔数最多为(N+1)笔
     * needAuth:是否开启限流
     * needs:是否开启鉴权
     * @param json
     * @return
     */
    @AuthRequired
    @ApiOperation(value = "查询主节点数据-v1")
    @AnRateLimit(permitsPerSecond = 1,timeout = 5)
    @PostMapping(value = "test")
    public Object test(@RequestBody(required = false) String json){
        log.info("这是version = 1");
        List<Users> list = userService.selectListUsersMaster();
        log.info("主节点数据：{}", JSON.toJSONString(list));
        return "version1";
    }


}
