package com.scott.wiker.service.impl;

import com.scott.wiker.config.zk.ZookeeperConfig;
import com.scott.wiker.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @calssname:ZookeeperServiceImpl
 * @author:Mr.薛
 * @date:2021/2/1 18:19
 * @version:v1.0
 * @status:create
 */
@Slf4j
@Service
public class ZookeeperServiceImpl implements ZookeeperService {

    /**
     * 判断节点是否存在
     * @param path
     * @return
     */
    @Override
    public boolean isExistNode(String path) {
        CuratorFramework client = ZookeeperConfig.getClient();
        client.sync() ;
        try {
            Stat stat = client.checkExists().forPath(path);
            return client.checkExists().forPath(path) != null;
        } catch (Exception e) {
            log.error("isExistNode error...", e);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建节点
     * @param mode
     * @param path
     */
    @Override
    public void createNode(CreateMode mode, String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 递归创建所需父节点
//            client.create().creatingParentsIfNeeded().withMode(mode).forPath(path);
            client.create().creatingParentsIfNeeded().forPath(path);
        } catch (Exception e) {
            log.error("createNode error...", e);
            e.printStackTrace();
        }
    }

    /**
     * 创建节点
     * @param path
     * @param nodeData
     */
    @Override
    public void setNodeData(String path, String nodeData) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 设置节点数据
            client.setData().forPath(path, nodeData.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("setNodeData error...", e);
            e.printStackTrace();
        }
    }

    /**
     * 创建节点设置节点数据
     * @param mode
     * @param path
     * @param nodeData
     */
    @Override
    public void createNodeAndData(CreateMode mode, String path, String nodeData) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 创建节点，关联数据
            client.create().creatingParentsIfNeeded().withMode(mode)
                    .forPath(path,nodeData.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("createNode error...", e);
            e.printStackTrace();
        }
    }

    /**
     * 获取节点数据
     * @param path
     * @return
     */
    @Override
    public String getNodeData(String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 数据读取和转换
            byte[] dataByte = client.getData().forPath(path) ;
            String data = new String(dataByte,"UTF-8") ;
            if (!StringUtils.isEmpty(data)){
                return data ;
            }
        }catch (Exception e) {
            log.error("getNodeData error...", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取节点下数据
     * @param path
     * @return
     */
    @Override
    public List<String> getNodeChild(String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        List<String> nodeChildDataList = new ArrayList<String>();
        try {
            // 节点下数据集
            nodeChildDataList = client.getChildren().forPath(path);
        } catch (Exception e) {
            log.error("getNodeChild error...", e);
            e.printStackTrace();
        }
        return nodeChildDataList;
    }

    /**
     * 是否递归删除节点
     * @param path
     * @param recursive
     */
    @Override
    public void deleteNode(String path, Boolean recursive) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            if(recursive) {
                // 递归删除节点
                client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
            } else {
                // 删除单个节点
                client.delete().guaranteed().forPath(path);
            }
        } catch (Exception e) {
            log.error("deleteNode error...", e);
            e.printStackTrace();
        }
    }

    /**
     * 获取读写锁
     * @param path
     * @return
     */
    @Override
    public InterProcessReadWriteLock getReadWriteLock(String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        // 写锁互斥、读写互斥
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, path);
        return readWriteLock ;
    }
}
