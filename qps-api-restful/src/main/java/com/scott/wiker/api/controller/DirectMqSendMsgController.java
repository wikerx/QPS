package com.scott.wiker.api.controller;

import com.scott.wiker.config.MsgContent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
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
 * @className :MqSendMsgController
 * @description : 消息发送者
 * @data :2020/11/18 0018 上午 10:01
 * @status : 编写
 **/
@Api("RabbitMQ-Direct接口管理")
@RestController
@RequestMapping(value = "/directMq")
@Slf4j
public class DirectMqSendMsgController {

    /**
     * 读取和写入消息队列
     */
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    AmqpTemplate amqpTemplate;

    /**
     * 直连交换机创建消息
     * @return
     */
    @ApiOperation(value = "直连交换机创建消息")
    @RequestMapping(value = "sendDirectMessage")
    public String sendDirectMessage(){
        for (int i = 0; i < 100; i++) {
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("msgId",String.valueOf(i));
            map.put("messageData","这是一个数据测试");
            map.put("createTime",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//            将消息携带绑定键值 发送到交换机 根据键值1，将消息放置在队列中
            rabbitTemplate.convertAndSend(MsgContent.DIRECT_EXCHANGE, MsgContent.DIRECT_ROUTING_MATCH_KEY1, map);
//            根据键值1，将消息放置在队列中
            rabbitTemplate.convertAndSend(MsgContent.LONELY_DIRECT_EXCHANGE, MsgContent.DIRECT_ROUTING_MATCH_KEY2,map);
//            绑定无交换机的直连队列
            amqpTemplate.convertAndSend(MsgContent.NO_EXCHANGE_QUEUE1,map.toString());
        }
        return "直接交换机信息写入100条成功！";
    }

}
