package com.scott.wiker.config.mq;

import com.scott.wiker.config.MsgContent;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :FanoutRabbitConfig
 * @description : 扇形交换机 一般用于消息的订阅、推送，即将消息推送给不同的消息队列，由消息队列去处理消息
 * 扇形交换机的路由键无效，所以不需要配置
 * @data :2020/11/18 0018 下午 3:16
 * @status : 编写
 **/
@Configuration
public class FanoutRabbitConfig {

    /**
     * 创建对列1
     * @return
     */
    @Bean
    public Queue fanoutQueue1(){
        return new Queue(MsgContent.FANOUT_SWITCH_QUEUE1);
    }

    /**
     * 创建对列2
     * @return
     */
    @Bean
    public Queue fanoutQueue2(){
        return new Queue(MsgContent.FANOUT_SWITCH_QUEUE2);
    }

    /**
     * 创建对列3
     * @return
     */
    @Bean
    public Queue fanoutQueue3(){
        return new Queue(MsgContent.FANOUT_SWITCH_QUEUE3);
    }

    /**
     * 创建扇形交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(MsgContent.FANOUT_EXCHANGE);
    }

    /**
     * 将三个队列分别绑定至扇形交换机上
     * @return
     */
    @Bean
    public Binding bindingExchangeFanout1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingExchangeFanout2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingExchangeFanout3() {
        return BindingBuilder.bind(fanoutQueue3()).to(fanoutExchange());
    }

}
