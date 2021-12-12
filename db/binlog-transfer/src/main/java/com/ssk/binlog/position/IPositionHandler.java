package com.ssk.binlog.position;

import com.ssk.binlog.BinlogException;
import com.ssk.binlog.config.SyncConfig;

/**
 * 处理 binlog 位点信息
 * @author 惊云
 * @date 2021/12/12 10:45
 */
public interface IPositionHandler {

    /**
     * 获得持久化位点信息
     * @param syncConfig
     * @return
     * @throws BinlogException
     */
    BinlogPositionEntity getPosition(SyncConfig syncConfig) throws BinlogException;

    /**
     * 持久化位点信息
     * @param syncConfig
     * @param binlogPositionEntity
     * @throws BinlogException
     */
    void savePosition(SyncConfig syncConfig, BinlogPositionEntity binlogPositionEntity) throws BinlogException;
}
