package com.ssk.configuration;

import com.ssk.rule.DefaultGrayRuleStrategy;
import com.ssk.rule.GrayRuleStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-06-25
 */
@Configuration
public class AutoGrayConfiguration {

    @Bean
    public GrayRuleStrategy grayRuleStrategy(){
        return new DefaultGrayRuleStrategy();
    }

}
