package com.scott.wiker.config.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @calssname:ZookeeperConfig
 * @author:Mr.薛
 * @date:2021/2/1 17:57
 * @version:v1.0
 * @status:create
 */
@Slf4j
@Configuration
public class ZookeeperConfig {
    @Resource
    private ZookeeperParam zookeeperParam ;

    private static CuratorFramework client = null ;

    /**
     * 初始化
     */
    @PostConstruct
    public void init (){
        //重试策略，初试时间1秒，重试10次
        RetryPolicy policy = new ExponentialBackoffRetry(
                zookeeperParam.getBaseSleepTimeMs(),
                zookeeperParam.getMaxRetries());
        //通过工厂创建Curator
        client = CuratorFrameworkFactory.builder()
                .connectString(zookeeperParam.getServer())
                .authorization("digest",zookeeperParam.getDigest().getBytes())
                .connectionTimeoutMs(zookeeperParam.getConnectionTimeoutMs())
                .sessionTimeoutMs(zookeeperParam.getSessionTimeoutMs())
                .retryPolicy(policy).build();
        //开启连接
        client.start();
        log.info("zookeeper 初始化完成...");
    }

    /**
     * 构建客户端连接
     * @return
     */
    public static CuratorFramework getClient (){
        return client ;
    }

    /**
     * 关闭连接
     */
    public static void closeClient (){
        if (client != null){
            client.close();
        }
    }


}
