package com.scott.wiker.config.mq;

import com.scott.wiker.config.MsgContent;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :TopicRabbitConfig
 * @description :Topic Exchange 主题交换机，根据不同的交换策略将消息分发至不同的队列中用于不同的消费
 * @data :2020/11/18 0018 下午 2:22
 * @status : 编写
 **/

@Configuration
public class TopicRabbitConfig {

    /**
     * 创建主题对列1
     * @return
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue(MsgContent.TOPIC_SWITCH_QUEUE1);
    }

    /**
     * 创建主题对列2
     * @return
     */
    @Bean
    public Queue topicQueue2(){
        return new Queue(MsgContent.TOPIC_SWITCH_QUEUE2);
    }

    /**
     * 创建主题对列3
     * @return
     */
    @Bean
    public Queue topicQueue3(){
        return new Queue(MsgContent.TOPIC_SWITCH_QUEUE3);
    }

    /**
     * 创建主题对列4
     * @return
     */
    @Bean
    public Queue topicQueue4(){
        return new Queue(MsgContent.TOPIC_SWITCH_QUEUE4);
    }

    /**
     * 创建一个交换机用于将消息分发至不同的队列中
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(MsgContent.TOPIC_EXCHANGE);
    }

    /**
     * 将队列1绑定至topic交换机，只要键值为：topic.order的路由键都会分配至队列1中
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(MsgContent.TOPIC_ROUTING_MATCH_KEY1);
    }

    /**
     * 将队列2绑定至topic交换机，只要键值为：topic.notify的路由键都会分配至队列2中
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(MsgContent.TOPIC_ROUTING_MATCH_KEY2);
    }

    /**
     * 将队列3绑定至topic交换机，只要键值为：topic.开头的，且后面只有一个单词的路由键都会分配至队列3中
     * @return
     */
    @Bean
    Binding bindingExchangeMessage3() {
        return BindingBuilder.bind(topicQueue3()).to(topicExchange()).with(MsgContent.TOPIC_ROUTING_MATCH_KEY3);
    }


    /**
     * 将队列4绑定至topic交换机，只要键值为：topic.开头的路由键都会分配至队列4中
     * @return
     */
    @Bean
    Binding bindingExchangeMessage4() {
        return BindingBuilder.bind(topicQueue4()).to(topicExchange()).with(MsgContent.TOPIC_ROUTING_MATCH_KEY4);
    }


}
