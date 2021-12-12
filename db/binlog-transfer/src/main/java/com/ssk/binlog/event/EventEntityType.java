package com.ssk.binlog.event;

/**
 * @author 惊云
 * @date 2021/12/12 10:37
 */
public enum EventEntityType {
    UPDATE("update"),
    INSERT("insert"),
    DELETE("delete");

    String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    EventEntityType(String desc) {
        this.desc = desc;
    }
}
