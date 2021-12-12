package com.ssk.binlog.event.lifecycle;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.ssk.binlog.config.SyncConfig;

/**
 * @author 惊云
 * @date 2021/12/12 11:15
 */
public class BaseLifeCycleListenerFactory implements ILifeCycleFactory{
    @Override
    public BinaryLogClient.LifecycleListener getLifeCycleListener(SyncConfig syncConfig) {
        return new BaseLifeCycleEventListener(syncConfig);
    }
}
