package com.scott.wiker.service;

import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @calssname:ZookeeperService
 * @author:Mr.薛
 * @date:2021/2/1 18:19
 * @version:v1.0
 * @status:create
 */
public interface ZookeeperService {
    /**
     * 判断节点是否存在
     */
    boolean isExistNode(final String path) ;
    /**
     * 创建节点
     */
    void createNode(CreateMode mode, String path) ;
    /**
     * 设置节点数据
     */
    void setNodeData(String path, String nodeData) ;
    /**
     * 创建节点
     */
    void createNodeAndData(CreateMode mode, String path, String nodeData) ;
    /**
     * 获取节点数据
     */
    String getNodeData(String path) ;
    /**
     * 获取节点下数据
     */
    List<String> getNodeChild(String path) ;
    /**
     * 是否递归删除节点
     */
    void deleteNode(String path, Boolean recursive) ;
    /**
     * 获取读写锁
     */
    InterProcessReadWriteLock getReadWriteLock(String path) ;

}
