package com.ssk.cloud.loadbalancer.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/18
 */
@Configuration
@ComponentScan("com.ssk")
@ConfigurationProperties(prefix = "gray")
@Data
public class GrayConfiguration {

    /**
     * 灰度机器优先，不根据请求头判断
     */
    @Value("${gray.header: false}")
    private Boolean grayHeader;

}
