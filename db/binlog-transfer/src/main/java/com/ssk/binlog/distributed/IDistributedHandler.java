package com.ssk.binlog.distributed;


import com.ssk.binlog.BinlogException;
import com.ssk.binlog.config.BinlogConfig;

public interface IDistributedHandler {
    void start(BinlogConfig binlogConfig) throws BinlogException;
}
