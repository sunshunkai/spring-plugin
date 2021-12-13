package com.ssk.consumer;

/**
 * 消费者抽象
 * @author 惊云
 * @date 2021/12/13 9:45
 */
public abstract class AbstractConsumer<T> {

    public abstract String consumer(T t);
}
