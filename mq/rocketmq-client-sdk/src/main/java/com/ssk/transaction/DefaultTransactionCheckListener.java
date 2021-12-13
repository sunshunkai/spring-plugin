package com.ssk.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 事务消息回查
 *
 * @author 惊云
 * @date 2021/12/13 9:29
 */
public class DefaultTransactionCheckListener implements TransactionCheckListener {

    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
        return null;
    }

}
