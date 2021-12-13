package com.ssk.consumer;

import com.ssk.config.MQProperties;
import com.ssk.consumer.listener.DefaultMessageListenerConcurrently;
import com.ssk.producer.MQProducer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消费端统一消息分发，目前只支持 push 模式
 *
 * @author 惊云
 * @date 2021/12/12 21:16
 */
public class Consumer implements DisposableBean {

    private MQProperties mqProperties;
    private Map<String, AbstractConsumer> consumerMap = new HashMap<>();
    private DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer();
    private DefaultMQPullConsumer pullConsumer = new DefaultMQPullConsumer();

    public void init(){
        pushConsumer =
                new DefaultMQPushConsumer("PushConsumer");
        pushConsumer.setNamesrvAddr("127.0.0.1:9876");
        try {
            //订阅PushTopic下Tag为push的消息
            pushConsumer.subscribe("PushTopic", "push");

            //程序第一次启动从消息队列头取数据
            pushConsumer.setConsumeFromWhere(
                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            pushConsumer.registerMessageListener(new DefaultMessageListenerConcurrently());
            pushConsumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        pushConsumer.shutdown();
    }
}
