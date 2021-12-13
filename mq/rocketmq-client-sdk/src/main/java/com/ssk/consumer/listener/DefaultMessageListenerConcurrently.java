package com.ssk.consumer.listener;

import com.ssk.anno.MQConsumer;
import com.ssk.common.MQConstant;
import com.ssk.common.MessageDelayLevel;
import com.ssk.consumer.AbstractConsumer;
import com.ssk.producer.MQProducer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 惊云
 * @date 2021/12/13 15:04
 */
public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently, ApplicationListener<ContextRefreshedEvent> {

    private Map<String, AbstractConsumer> consumerMap = new HashMap<>();
    private MQProducer mqProducer;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        Message msg = list.get(0);

        AbstractConsumer consumer = consumerMap.get(msg.getTags());
        if(nextDelayQueue(msg)){
            consumer.consumer(null);
        }else {
            mqProducer.sendDelay(null,0L);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private boolean nextDelayQueue(Message message){
        String OUT_DELAY = message.getProperties().get(MQConstant.OUT_DELAY);
        if(StringUtils.isEmpty(OUT_DELAY)){
            return false;
        }
        Long delayTime = Long.parseLong(OUT_DELAY);
        int maxDelayLevel = MessageDelayLevel.getMaxDelayLevel(delayTime);
        Long surplusDelayTime = MessageDelayLevel.getSurplusDelayTime(delayTime, maxDelayLevel);
        message.setDelayTimeLevel(maxDelayLevel);
        if( surplusDelayTime != 0){
            message.getProperties().put(MQConstant.OUT_DELAY,String.valueOf(surplusDelayTime));
        }
        return true;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        Map<String, AbstractConsumer> mqConsumerMap = applicationContext.getBeansOfType(AbstractConsumer.class);
        for(String key : mqConsumerMap.keySet()){
            AbstractConsumer consumer = mqConsumerMap.get(key);
            MQConsumer annotation = consumer.getClass().getAnnotation(MQConsumer.class);
            consumerMap.put(annotation.tag(),consumer);
        }

    }
}


