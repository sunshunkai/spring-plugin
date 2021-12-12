package com.ssk.binlog.event.parser;

import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.ssk.binlog.BinlogException;
import com.ssk.binlog.event.EventEntity;
import com.ssk.binlog.event.EventEntityType;
import com.ssk.binlog.event.parser.converter.CommonConverterProcessor;
import com.ssk.binlog.tablemeta.TableMetaEntity;
import com.ssk.binlog.tablemeta.TableMetaFactory;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InsertEventParser implements IEventParser {
    CommonConverterProcessor commonConverterProcessor = new CommonConverterProcessor();

    TableMetaFactory tableMetaFactory;

    public InsertEventParser(TableMetaFactory tableMetaFactory) {
        this.tableMetaFactory = tableMetaFactory;
    }

    @Override
    public List<EventEntity> parse(Event event) throws BinlogException {
        WriteRowsEventData writeRowsEventData = event.getData();
        TableMetaEntity tableMetaEntity = tableMetaFactory.getTableMetaEntity(writeRowsEventData.getTableId());
        List<Serializable[]> rows = writeRowsEventData.getRows();
        List<EventEntity> eventEntityList = new ArrayList<>();
        rows.forEach(rowMap -> {
            List<TableMetaEntity.ColumnMetaData> columnMetaDataList = tableMetaEntity.getColumnMetaDataList();
            String[] after = commonConverterProcessor.convertToString(rowMap, columnMetaDataList);
            List<String> columns = new ArrayList<>();
            List<Object> changeAfter = new ArrayList<>();
            for (int i = 0; i < after.length; i++) {
                columns.add(columnMetaDataList.get(i).getName());
                changeAfter.add(after[i]);
            }

            EventEntity eventEntity = new EventEntity();
            eventEntity.setEvent(event);
            eventEntity.setEventEntityType(EventEntityType.INSERT);
            eventEntity.setDatabaseName(tableMetaEntity.getDbName());
            eventEntity.setTableName(tableMetaEntity.getTableName());
            eventEntity.setColumns(columnMetaDataList);
            eventEntity.setChangeAfter(changeAfter);

            eventEntityList.add(eventEntity);
        });
        return eventEntityList;
    }
}
