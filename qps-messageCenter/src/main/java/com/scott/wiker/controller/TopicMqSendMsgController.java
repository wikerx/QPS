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
 * @className :TopicMqSendMsgController
 * @description :将消息写入Topic主题划分的队列中
 * @data :2020/11/18 0018 下午 2:48
 * @status : 编写
 **/
@RestController
@RequestMapping(value = "/topicMq")
public class TopicMqSendMsgController {

    /**
     * 读取和写入消息队列
     */
    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * 话题交换机创建消息
     * @return
     */
    @RequestMapping(value = "sendTopicMessage")
    public String sendTopicMessage(){
        for (int i = 0; i < 100; i++) {
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("msgId",String.valueOf(i));
            map1.put("messageData","这是一个【order】测试");
            map1.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            Map<String,Object> map2 = new HashMap<String,Object>();
            map2.put("msgId",String.valueOf(i));
            map2.put("messageData","这是一个【notify】测试");
            map2.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            Map<String,Object> map3 = new HashMap<String,Object>();
            map3.put("msgId",String.valueOf(i));
            map3.put("messageData","这是一个【order-1】测试");
            map3.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            Map<String,Object> map4 = new HashMap<String,Object>();
            map4.put("msgId",String.valueOf(i));
            map4.put("messageData","这是一个【notify-1】测试");
            map4.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

//            将消息携带绑定键值 发送到交换机 根据键值1，将消息放置在队列1中
            rabbitTemplate.convertAndSend(MsgContent.TOPIC_EXCHANGE, MsgContent.TOPIC_ROUTING_MATCH_KEY1, map1.toString());
//            将消息携带绑定键值 发送到交换机 根据键值2，将消息放置在队列2中
            rabbitTemplate.convertAndSend(MsgContent.TOPIC_EXCHANGE, MsgContent.TOPIC_ROUTING_MATCH_KEY2,map2.toString());
//            将消息携带绑定键值 发送到交换机 根据键值3，将消息放置在队列3中(topic.后面一个单词的都会被放入)
            rabbitTemplate.convertAndSend(MsgContent.TOPIC_EXCHANGE, MsgContent.TOPIC_ROUTING_MATCH_KEY3,map3.toString());
//            将消息携带绑定键值 发送到交换机 根据键值4，将消息放置在队列4中(topic.的都会被放入)
            rabbitTemplate.convertAndSend(MsgContent.TOPIC_EXCHANGE, MsgContent.TOPIC_ROUTING_MATCH_KEY4,map4.toString());
//            所以按照以上规则队列1中存在一条；队列2中1条；队列3和4中都是4条
        }
        return "Topic交换机信息写入100条成功！";
    }


}
