package com.ssk.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author 惊云
 * @date 2021/12/12 21:26
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MQConsumer {

    /** 目前系统只支持单 topic  */
    String topic();

    String tag();

}
