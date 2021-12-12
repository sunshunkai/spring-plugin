package com.ssk.binlog.event.parser;


import com.ssk.binlog.BinlogException;
import com.ssk.binlog.config.SyncConfig;
import com.ssk.binlog.tablemeta.TableMetaFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件解析器工厂
 */
public class EventParserFactory {
    private static final Logger log = LoggerFactory.getLogger(EventParserFactory.class);

    /**
     * 获取事件解析调度器
     *
     * @param syncConfig
     * @return
     */
    public static IEventParserDispatcher getEventParserDispatcher(SyncConfig syncConfig) throws BinlogException {
        //目前只有一种解析器，这里可扩展为根据syncConfig的配置获取不同的解析器
        return new CommonEventParserDispatcher(new TableMetaFactory(syncConfig));
    }
}
