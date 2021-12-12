package com.ssk.binlog.config;

import com.ssk.binlog.distributed.IDistributedHandler;
import com.ssk.binlog.event.lifecycle.BaseLifeCycleListenerFactory;
import com.ssk.binlog.event.lifecycle.ILifeCycleFactory;
import com.ssk.binlog.factory.BinaryLogClientFactory;
import com.ssk.binlog.factory.IClientFactory;
import com.ssk.binlog.position.IPositionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 惊云
 * @date 2021/12/12 10:55
 */
public class BinlogConfig {
    //配置列表
    Map<String, SyncConfig> syncConfigList = new HashMap<>();

    //binlog位点处理器
    IPositionHandler positionHandler;

    //分布式处理器
    IDistributedHandler distributedHandler;

    //LifeCycleEvent监听器
    ILifeCycleFactory lifeCycleFactory = new BaseLifeCycleListenerFactory();

    IClientFactory clientFactory = new BinaryLogClientFactory();

    //增加配置项
    public void addSyncConfig(String key, SyncConfig syncConfig) {
        syncConfigList.put(key, syncConfig);
    }

    public Map<String, SyncConfig> getSyncConfigMap() {
        return syncConfigList;
    }

    public IPositionHandler getPositionHandler() {
        return positionHandler;
    }

    public void setPositionHandler(IPositionHandler positionHandler) {
        this.positionHandler = positionHandler;
    }

    public IDistributedHandler getDistributedHandler() {
        return distributedHandler;
    }

    public void setDistributedHandler(IDistributedHandler distributedHandler) {
        this.distributedHandler = distributedHandler;
    }

    public ILifeCycleFactory getLifeCycleFactory() {
        return lifeCycleFactory;
    }

    public void setLifeCycleFactory(ILifeCycleFactory lifeCycleFactory) {
        this.lifeCycleFactory = lifeCycleFactory;
    }

    public IClientFactory getClientFactory() {
        return clientFactory;
    }

    public void setClientFactory(IClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}
