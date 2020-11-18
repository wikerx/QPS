package com.scott.wiker.config.mq;

import com.scott.wiker.config.MsgContent;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :DirectRabbitConfig
 * @description : 直连交换机：所有发送到Direct Exchange的消息会被转发到RoutingKey指定的Queue
 * Direct模式可以使用RabbitMQ自带的Exchange：Default Exchange，所以不需要将Exchange进行任何绑定操作，消息传递时，RoutingKey必须完全匹配才能被队列接收，否则会被抛弃。
 * 直连交换机的特点是：一对一，通过匹配键或者通过直连方式直接将数据录入队列中，这种一般用于一对一消费，不存在重复消费
 * @data :2020/11/18 0018 上午 9:46
 * @status : 编写
 **/
@Configuration
public class DirectRabbitConfig {

    /**
     * 创建并初始化一个直连队列
     * @return
     */
    @Bean
    public Queue directSwitchQueue1() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue(MsgContent.DIRECTLY_CONNECTED_SWITCH_QUEUE1,true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(MsgContent.DIRECTLY_CONNECTED_SWITCH_QUEUE1,true);
    }


    /**
     * 创建一个直连交换机
     * @return
     */
    @Bean
    DirectExchange directExchange() {
//        长期不用-删除
        //  return new DirectExchange(MsgContent.DIRECT_EXCHANGE,true,true);
//        长期不用不删除
        return new DirectExchange(MsgContent.DIRECT_EXCHANGE,true,false);
    }

    /**
     * 绑定  将队列和交换机绑定, 并设置用于匹配键
     * @return
     */
    @Bean
    Binding bindingDirect1() {
        return BindingBuilder.bind(directSwitchQueue1()).to(directExchange()).with(MsgContent.DIRECT_ROUTING_MATCH_KEY1);
    }


    /**
     * 创建并初始化一个直连队列
     * @return
     */
    @Bean
    public Queue directSwitchQueue2() {
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(MsgContent.DIRECTLY_CONNECTED_SWITCH_QUEUE2,true);
    }

    /**
     * 创建一个简易直连交换机
     * @return
     */
    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange(MsgContent.LONELY_DIRECT_EXCHANGE,true,false);
    }

    /**
     * 绑定  将队列和交换机绑定, 并设置用于匹配键
     * @return
     */
    @Bean
    Binding bindingDirect2() {
        return BindingBuilder.bind(directSwitchQueue2()).to(lonelyDirectExchange()).with(MsgContent.DIRECT_ROUTING_MATCH_KEY2);
    }

    /**
     * 创建无交换机队列
     * @return
     */
    @Bean
    public Queue noExchangeQueue(){
        return new Queue(MsgContent.NO_EXCHANGE_QUEUE1);
    }

}