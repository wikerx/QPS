package com.scott.wiker.consumer;

import com.rabbitmq.client.Channel;
import com.scott.wiker.config.MsgContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :DirectConsumer
 * @description : 直连交换机消费者 消费的数据类型必须和写入的数据类型一致
 * @data :2020/11/18 0018 上午 11:17
 * @status : 编写
 **/
@Component
@Slf4j
public class DirectConsumer {

    /**
     * 用于消费具体队列
     */
    @RabbitListener(queues = MsgContent.DIRECTLY_CONNECTED_SWITCH_QUEUE1)
    @RabbitHandler
    public void consumeQueue1(Map<String,Object> map, Channel channel, Message message) throws IOException {
        try {
//            一旦发生异常，会重新读取此消息，消息一直存在
//            int i = 1/0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("信息已消费-有键值匹配：{}  ：{}", MsgContent.DIRECTLY_CONNECTED_SWITCH_QUEUE1, map.toString());
        }catch (Exception e){
            //消费者处理出了问题，需要告诉队列信息消费失败-拒绝消息，重新放入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
            //消费者处理出了问题，需要告诉队列信息消费失败-拒绝消息，移除队列，不在使用此消息
//            channel.basicNack(
//                    message.getMessageProperties().getDeliveryTag(),
//                    false,false);
            e.printStackTrace();
            log.error("消息消费失败：{}",map.toString());
        }
    }

    /**
     * 用于消费具体队列
     */
    @RabbitListener(queues = MsgContent.DIRECTLY_CONNECTED_SWITCH_QUEUE2)
    @RabbitHandler
    public void consumeQueue2(Map<String,Object> map, Channel channel, Message message) throws IOException {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("信息已消费-有键值匹配：{}  ：{}",MsgContent.DIRECTLY_CONNECTED_SWITCH_QUEUE2,map.toString());
        }catch (Exception e){
            //消费者处理出了问题，需要告诉队列信息消费失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
            e.printStackTrace();
            log.error("消息消费失败：{}",map.toString());
        }
    }

    /**
     * 用于消费具体队列
     */
    @RabbitListener(queues = MsgContent.NO_EXCHANGE_QUEUE1)
    @RabbitHandler
    public void consumeQueue3(String value, Channel channel, Message message) throws IOException {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("信息已消费-无键值匹配：{}  ：{}",MsgContent.NO_EXCHANGE_QUEUE1,value);
        }catch (Exception e){
            //消费者处理出了问题，需要告诉队列信息消费失败
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
            e.printStackTrace();
            log.error("消息消费失败：{}",value);
        }
    }



}
