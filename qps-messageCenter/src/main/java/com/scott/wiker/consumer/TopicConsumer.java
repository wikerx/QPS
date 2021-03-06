package com.scott.wiker.consumer;

import com.rabbitmq.client.Channel;
import com.scott.wiker.config.MsgContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :TopicConsumer
 * @description : 主题交换机消费者 消费的数据类型必须和写入的数据类型一致
 * @data :2020/11/18 0018 下午 3:12
 * @status : 编写
 **/
@Component
@Slf4j
public class TopicConsumer {

    /**
     * 用于消费队列1
     */
    @RabbitListener(queues = MsgContent.TOPIC_SWITCH_QUEUE1)
    @RabbitHandler
    public void consumeQueue1(String value, Channel channel, Message message) throws IOException {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("信息已消费-队列：{}  内容：{}",MsgContent.TOPIC_SWITCH_QUEUE1,value);
        }catch (Exception e){
            //消费者处理出了问题，需要告诉队列信息消费失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
            e.printStackTrace();
            log.error("消息消费失败：{}",value);
        }
    }

    /**
     * 用于消费队列2
     */
    @RabbitListener(queues = MsgContent.TOPIC_SWITCH_QUEUE2)
    @RabbitHandler
    public void consumeQueue2(String value, Channel channel, Message message) throws IOException {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("信息已消费-队列：{}  内容：{}",MsgContent.TOPIC_SWITCH_QUEUE2,value);
        }catch (Exception e){
            //消费者处理出了问题，需要告诉队列信息消费失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
            e.printStackTrace();
            log.error("消息消费失败：{}",value);
        }
    }

    /**
     * 用于消费队列3
     */
    @RabbitListener(queues = MsgContent.TOPIC_SWITCH_QUEUE3)
    @RabbitHandler
    public void consumeQueue3(String value, Channel channel, Message message) throws IOException {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("信息已消费-队列：{}  内容：{}",MsgContent.TOPIC_SWITCH_QUEUE3,value);
        }catch (Exception e){
            //消费者处理出了问题，需要告诉队列信息消费失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
            e.printStackTrace();
            log.error("消息消费失败：{}",value);
        }
    }

    /**
     * 用于消费队列4
     */
    @RabbitListener(queues = MsgContent.TOPIC_SWITCH_QUEUE4)
    @RabbitHandler
    public void consumeQueue4(String value, Channel channel, Message message) throws IOException {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("信息已消费-队列：{}  内容：{}",MsgContent.TOPIC_SWITCH_QUEUE4,value);
        }catch (Exception e){
            //消费者处理出了问题，需要告诉队列信息消费失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
            e.printStackTrace();
            log.error("消息消费失败：{}",value);
        }
    }


}
