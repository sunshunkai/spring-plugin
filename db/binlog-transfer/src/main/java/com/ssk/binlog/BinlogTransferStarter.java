package com.ssk.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.ssk.binlog.config.BinlogConfig;
import com.ssk.binlog.config.RedisConfig;
import com.ssk.binlog.config.SyncConfig;
import com.ssk.binlog.distributed.RedisDistributedHandler;
import com.ssk.binlog.factory.IClientFactory;
import com.ssk.binlog.position.IPositionHandler;
import com.ssk.binlog.position.RedisPositionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 惊云
 * @date 2021/12/12 10:52
 */
public class BinlogTransferStarter {
    private final Logger log = LoggerFactory.getLogger(BinlogTransferStarter.class);

    private BinlogConfig binlogConfig;

    public BinlogConfig getBinlogConfig() {
        return binlogConfig;
    }

    public void setBinlogConfig(BinlogConfig binlogConfig) {
        this.binlogConfig = binlogConfig;
    }

    /**
     * main start method
     */
    public void start() throws BinlogException {
        if (binlogConfig.getDistributedHandler() != null) {
            binlogConfig.getDistributedHandler().start(binlogConfig);
        } else {
            singleStart();
        }
    }

    private void singleStart() {
        //新建工厂对象
        IClientFactory binaryLogClientFactory = binlogConfig.getClientFactory();
        binaryLogClientFactory.setPositionHandler(binlogConfig.getPositionHandler());
        binaryLogClientFactory.setLifeCycleFactory(binlogConfig.getLifeCycleFactory());

        //生成全部client
        List<BinaryLogClient> binaryLogClientList = new ArrayList<>();
        binlogConfig.getSyncConfigMap().forEach((key, syncConfig) -> {
            try {
                binaryLogClientList.add(binaryLogClientFactory.getClient(syncConfig));
            } catch (BinlogException e) {
                log.error(e.getMessage(), e);
            }
        });

        //执行
        binaryLogClientList.forEach(binaryLogClient -> {
            new Thread(() -> {
                try {
                    binaryLogClient.setHeartbeatInterval(10 * 1000L);
                    binaryLogClient.connect();
                } catch (IOException e) {
                    log.error("binaryLogClient connect error!" + binaryLogClient.toString());
                }
            }).start();
        });
    }

    public BinaryLogClient getClientByDbKey(String key) {
        SyncConfig syncConfig = binlogConfig.getSyncConfigMap().get(key);
        if (syncConfig == null) {
            return null;
        }
        return binlogConfig.getClientFactory().getCachedClient(syncConfig);
    }

    public static void main(String[] args) {
        SyncConfig syncConfig = new SyncConfig();
        syncConfig.setHost("localhost");
        syncConfig.setPort(3306);
        syncConfig.setUserName("root");
        syncConfig.setPassword("123456");
        syncConfig.setEventHandlerList(Collections.singletonList(eventEntity -> System.out.println(eventEntity.getJsonFormatData())));

        BinlogConfig binlogConfig = new BinlogConfig();
        binlogConfig.addSyncConfig("d1", syncConfig);

        RedisConfig redisConfig = new RedisConfig("127.0.0.1", 6379);
        IPositionHandler redisPositionHandler = new RedisPositionHandler(redisConfig);
        binlogConfig.setPositionHandler(redisPositionHandler);

        binlogConfig.setDistributedHandler(new RedisDistributedHandler(redisConfig));

        BinlogTransferStarter binlogTransferStarter = new BinlogTransferStarter();
        binlogTransferStarter.setBinlogConfig(binlogConfig);
        try {
            binlogTransferStarter.start();
        } catch (BinlogException e) {
            e.printStackTrace();
        }
    }
}
