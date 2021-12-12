package com.ssk.binlog.factory;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.ssk.binlog.BinlogException;
import com.ssk.binlog.config.SyncConfig;
import com.ssk.binlog.event.lifecycle.ILifeCycleFactory;
import com.ssk.binlog.position.IPositionHandler;

/**
 * @author 惊云
 * @date 2021/12/12 10:55
 */
public interface IClientFactory {

    BinaryLogClient getClient(SyncConfig syncConfig) throws BinlogException;

    BinaryLogClient getCachedClient(SyncConfig syncConfig);

    void setPositionHandler(IPositionHandler positionHandler);

    IPositionHandler getPositionHandler();

    void setLifeCycleFactory(ILifeCycleFactory lifeCycleFactory);

    ILifeCycleFactory getLifeCycleFactory();
}
