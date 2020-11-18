package com.scott.wiker.config;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :MsgContent
 * @description : 消息-缓存命名及初始化数据
 * @data :2020/11/18 0018 上午 9:48
 * @status : 编写
 **/
public class MsgContent {
    /**
     * 直连交换机队列
     */
    public final static String DIRECTLY_CONNECTED_SWITCH_QUEUE1 = "directSwitchQueue1";
    public final static String DIRECTLY_CONNECTED_SWITCH_QUEUE2 = "directSwitchQueue2";

    public final static String NO_EXCHANGE_QUEUE1 = "noDirectQueue1";

    public final static String LONELY_DIRECT_EXCHANGE = "lonelyDirectExchange";
    /**
     * 交换机名称
     */
    public final static String DIRECT_EXCHANGE = "directExchange";
    /**
     * 直连交换机匹配键
     */
    public final static String DIRECT_ROUTING_MATCH_KEY1 = "directRoutingMatchKey1";
    public final static String DIRECT_ROUTING_MATCH_KEY2 = "directRoutingMatchKey2";

    /**
     * Topic主题交换机
     */
    public final static String TOPIC_EXCHANGE = "topicExchange";
    /**
     * Topic主题交换机对列
     */
    public final static String TOPIC_SWITCH_QUEUE1 = "topicSwitchQueue1";
    public final static String TOPIC_SWITCH_QUEUE2 = "topicSwitchQueue2";
    public final static String TOPIC_SWITCH_QUEUE3 = "topicSwitchQueue3";
    public final static String TOPIC_SWITCH_QUEUE4 = "topicSwitchQueue4";
    /**
     * 主题交换机匹配键
     * * 表示匹配任意一个单词
     * # 表示匹配任意一个或多个单词
     */
    public final static String TOPIC_ROUTING_MATCH_KEY1 = "topic.order";
    public final static String TOPIC_ROUTING_MATCH_KEY2 = "topic.notify";
    public final static String TOPIC_ROUTING_MATCH_KEY3 = "topic.*";
    public final static String TOPIC_ROUTING_MATCH_KEY4 = "topic.#";

    /**
     * fanoutExchange：扇形交换机
     */
    public final static String FANOUT_EXCHANGE = "fanoutExchange";
    /**
     * 扇形交换机队列
     */
    public final static String FANOUT_SWITCH_QUEUE1 = "fanoutSwitchQueue1";
    public final static String FANOUT_SWITCH_QUEUE2 = "fanoutSwitchQueue2";
    public final static String FANOUT_SWITCH_QUEUE3 = "fanoutSwitchQueue3";

}
