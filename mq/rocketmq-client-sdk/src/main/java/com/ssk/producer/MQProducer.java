package com.ssk.producer;

import com.ssk.config.MQProperties;
import com.ssk.message.AbstractMessage;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PostConstruct;

/**
 * @author 惊云
 * @date 2021/12/12 21:15
 */
public class MQProducer implements DisposableBean {

    private MQProperties mqProperties;
    /** 普通消息生产者 */
    private DefaultMQProducer defaultMQProducer;
    /** 事务消息生产者 */
    private TransactionMQProducer transactionMQProducer;

    @PostConstruct
    public void init() throws MQClientException {
        defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr(mqProperties.getNamesrvAddr());
        defaultMQProducer.start();

        transactionMQProducer = new TransactionMQProducer();
        transactionMQProducer.setNamesrvAddr(mqProperties.getNamesrvAddr());
        // 事务消息回查
        transactionMQProducer.setTransactionCheckListener(null);
        transactionMQProducer.start();
    }

    /**
     * 发送消息
     * @return
     */
    public boolean send(AbstractMessage message){
        return true;
    }

    /**
     * 发送事务消息
     * @return
     */
    public boolean sendTransaction(AbstractMessage message){
        return false;
    }

    /**
     * 发送延迟消息
     * @return
     */
    public boolean sendDelay(AbstractMessage message,long delayTime){
        //TODO 需要计算消息的延迟时间计算投入队列

        return false;
    }

    /**
     * 消息转换
     * @param abstractMessage
     * @return
     */
    private  Message convert(AbstractMessage abstractMessage){
        Message message = new Message(abstractMessage.getTopic(),abstractMessage.getTag(),null);
        return message;
    }

    @Override
    public void destroy() throws Exception {
        defaultMQProducer.shutdown();
        transactionMQProducer.shutdown();
    }
}
