package com.scott.wiker.controller;

import com.scott.wiker.config.MsgContent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :FanoutMqSendMsgController
 * @description : 扇形路由的特点：共享，即一个交换机上面的所有数据，其他绑定队列是可以共享数据的
 * @data :2020/11/18 0018 下午 3:27
 * @status : 编写
 **/
@RestController
@RequestMapping(value = "/fanoutMq")
public class FanoutMqSendMsgController {

    /**
     * 读取和写入消息队列
     */
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 扇形交换机创建消息
     * @return
     */
    @RequestMapping(value = "sendFanoutMessage")
    public String sendFanoutMessage(){
        for (int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("msgId", String.valueOf(i));
            map.put("messageData", "这是一个数据测试");
            map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//            路由键设置了也没用
            rabbitTemplate.convertAndSend(MsgContent.FANOUT_EXCHANGE,null,map.toString());
        }
        return "扇形交换机信息写入100条成功！";
    }

}
