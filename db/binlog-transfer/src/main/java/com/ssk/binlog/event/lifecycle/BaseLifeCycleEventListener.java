package com.ssk.binlog.event.lifecycle;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.ssk.binlog.config.SyncConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 惊云
 * @date 2021/12/12 11:15
 */
public class BaseLifeCycleEventListener implements BinaryLogClient.LifecycleListener{

    private final Logger log = LoggerFactory.getLogger(BaseLifeCycleEventListener.class);

    SyncConfig syncConfig;

    public BaseLifeCycleEventListener(SyncConfig syncConfig) {
        this.syncConfig = syncConfig;
    }

    @Override
    public void onConnect(BinaryLogClient client) {

    }

    @Override
    public void onCommunicationFailure(BinaryLogClient client, Exception ex) {
        log.error(syncConfig.getHost() + ":" + syncConfig.getPort() + "," + ex.getMessage() + "." + client.getBinlogFilename() + "/" + client.getBinlogPosition(), ex);
    }

    @Override
    public void onEventDeserializationFailure(BinaryLogClient client, Exception ex) {
        log.error(syncConfig.getHost() + ":" + syncConfig.getPort() + "," + ex.getMessage(), ex);
    }

    @Override
    public void onDisconnect(BinaryLogClient client) {

    }
}
