package com.ssk.common;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 处理 RocketMQ 延时消息，前提是不改变 RocketMQ 默认的延时级别
 * 延时级别：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
 *
 * @author 惊云
 * @date 2021/12/13 15:51
 */
public class MessageDelayLevel {

    private static final ConcurrentMap<Integer, Long> delayLevelTable =
                                    new ConcurrentHashMap<>(32);

    private static final ConcurrentMap<Long , Integer> delayTimeTable =
                                    new ConcurrentHashMap<>(32);

    private static List<Long> delayTimeList = Arrays.asList(1L,5L,10L,30L,60L,120L,180L,240L);

    /**
     * 从延时时间计算下一个需要投入的延时级别
     * @param delayTime 延时时间
     * @return
     */
    public static int getMaxDelayLevel(Long delayTime){
        for (int i = delayTimeList.size() - 1; i >= 0; i--) {
            Long delay = delayTimeList.get(i);
            if( Objects.equals(delayTime,delay) || delayTime >= delay ){
                return delayTimeTable.get(delay);
            }
        }
        return 0;
    }

    /**
     * 计算此次投递后剩余延时时间
     * @param delayTime 延时时间
     * @param nextDelayLevel 下个需要延时级别
     * @return 剩余延时时间
     */
    public static Long getSurplusDelayTime(Long delayTime,int nextDelayLevel){
        Long a = delayLevelTable.get(nextDelayLevel);
        return delayTime - a;
    }

    static {
        for (int i = 0; i < delayTimeList.size(); i++) {
            delayLevelTable.put(i+1,delayTimeList.get(i));
            delayTimeTable.put(delayTimeList.get(i),i+1);
        }
    }


}
