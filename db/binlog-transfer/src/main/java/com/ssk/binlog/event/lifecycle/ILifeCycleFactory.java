package com.ssk.binlog.event.lifecycle;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.ssk.binlog.config.SyncConfig;

/**
 * @author 惊云
 * @date 2021/12/12 11:14
 */
public interface ILifeCycleFactory {
    BinaryLogClient.LifecycleListener getLifeCycleListener(SyncConfig syncConfig);
}
