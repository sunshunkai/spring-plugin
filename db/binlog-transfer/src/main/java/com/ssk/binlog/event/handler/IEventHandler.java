package com.ssk.binlog.event.handler;

import com.ssk.binlog.BinlogException;
import com.ssk.binlog.event.EventEntity;

/**
 * @author 惊云
 * @date 2021/12/12 10:26
 */
public interface IEventHandler {
    void process(EventEntity eventEntity) throws BinlogException;
}
