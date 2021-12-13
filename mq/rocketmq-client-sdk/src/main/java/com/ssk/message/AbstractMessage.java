package com.ssk.message;

import lombok.Data;

import java.util.Map;

/**
 * @author 惊云
 * @date 2021/12/13 9:21
 */
@Data
public abstract class AbstractMessage<T> {

    protected String topic;
    protected String tag;
    /** 此次投递候剩余时间 */
    protected long delayTime;
    /** 消息属性 */
    protected Map<String, String> properties;
    /** 消息内容 */
    protected T body;

}
