package com.ssk.binlog.event;

import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventHeaderV4;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.RotateEventData;
import com.ssk.binlog.BinlogException;
import com.ssk.binlog.config.SyncConfig;
import com.ssk.binlog.event.handler.IEventHandler;
import com.ssk.binlog.event.parser.IEventParserDispatcher;
import com.ssk.binlog.position.BinlogPositionEntity;
import com.ssk.binlog.position.IPositionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 惊云
 * @date 2021/12/12 10:42
 */
public class MultiEventHandlerListener implements IEventListener{
    private final Logger log = LoggerFactory.getLogger(MultiEventHandlerListener.class);

    //配置信息
    SyncConfig syncConfig;

    //处理器列表
    List<IEventHandler> eventHandlerList = new ArrayList<>();

    //事件解析调度器
    IEventParserDispatcher eventParserDispatcher;

    //binlog位点处理器
    IPositionHandler positionHandler;

    public void registerEventHandler(IEventHandler eventHandler) {
        eventHandlerList.add(eventHandler);
    }

    public SyncConfig getSyncConfig() {
        return syncConfig;
    }

    public void setSyncConfig(SyncConfig syncConfig) {
        this.syncConfig = syncConfig;
    }

    public List<IEventHandler> getEventHandlerList() {
        return eventHandlerList;
    }

    public void setEventHandlerList(List<IEventHandler> eventHandlerList) {
        this.eventHandlerList = eventHandlerList;
    }

    public IEventParserDispatcher getEventParserDispatcher() {
        return eventParserDispatcher;
    }

    public void setEventParserDispatcher(IEventParserDispatcher eventParserDispatcher) {
        this.eventParserDispatcher = eventParserDispatcher;
    }

    public IPositionHandler getPositionHandler() {
        return positionHandler;
    }

    public void setPositionHandler(IPositionHandler positionHandler) {
        this.positionHandler = positionHandler;
    }

    @Override
    public void onEvent(Event event) {
        try {
            /*
             * 不计入position更新的事件类型
             * FORMAT_DESCRIPTION类型为binlog起始时间
             * HEARTBEAT为心跳检测事件，不会写入master的binlog，记录该事件的position会导致重启时报错
             */
            List<EventType> excludePositionEventType = new ArrayList<>();
            excludePositionEventType.add(EventType.FORMAT_DESCRIPTION);
            excludePositionEventType.add(EventType.HEARTBEAT);
            //获取当前事件的类型
            EventType eventType = event.getHeader().getEventType();
            if (!excludePositionEventType.contains(eventType)) {
                BinlogPositionEntity binlogPositionEntity = new BinlogPositionEntity();
                //处理rotate事件,这里会替换调binlog fileName
                if (event.getHeader().getEventType().equals(EventType.ROTATE)) {
                    RotateEventData rotateEventData = (RotateEventData) event.getData();
                    binlogPositionEntity.setBinlogName(rotateEventData.getBinlogFilename());
                    binlogPositionEntity.setPosition(rotateEventData.getBinlogPosition());
                    binlogPositionEntity.setServerId(event.getHeader().getServerId());
                } else { //统一处理事件对应的binlog position
                    binlogPositionEntity = positionHandler.getPosition(syncConfig);
                    EventHeaderV4 eventHeaderV4 = (EventHeaderV4) event.getHeader();
                    binlogPositionEntity.setPosition(eventHeaderV4.getPosition());
                    binlogPositionEntity.setServerId(event.getHeader().getServerId());
                }
                if (positionHandler != null) {
                    log.debug(event.toString() + "---" + binlogPositionEntity.toString());
                    positionHandler.savePosition(syncConfig, binlogPositionEntity);
                }
            }
            //解析事件为统一实体
            List<EventEntity> eventEntityList = eventParserDispatcher.parse(event);
            if (eventEntityList != null) {
                //循环调用处理器
                eventEntityList.forEach(eventEntity -> {
                    eventHandlerList.forEach(eventHandler -> {
                        try {
                            eventHandler.process(eventEntity);
                        } catch (BinlogException e) {
                            log.error(eventHandler.toString() + " process error:" + e.getMessage(), e);
                        }
                    });
                });
            }

        } catch (BinlogException e) {
            log.error(e.getMessage(), e);
        }
    }
}
