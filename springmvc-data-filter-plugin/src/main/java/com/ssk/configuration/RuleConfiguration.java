package com.ssk.configuration;


import com.ssk.service.json.fastjson.FastjsonDesensitizate;
import com.ssk.interceptor.DataFilterInterceptor;
import com.ssk.load.EnvLoadRule;
import com.ssk.load.LoadRule;
import com.ssk.service.json.CommonDesensitizate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
@Configuration
@ComponentScan()
public class RuleConfiguration implements WebMvcConfigurer {

    private static final String FLOW = "/api/trantor/flow";
    private static final String FUNC = "/api/trantor/func";

    @Autowired
    private RuleConfig ruleConfig;

    @Bean
    @ConditionalOnMissingBean
    public LoadRule loadRule(){
        return new EnvLoadRule();
    }

    @Bean
    @ConditionalOnMissingBean
    public CommonDesensitizate commonDesensitizate(){
        return new FastjsonDesensitizate(loadRule().loadRule());
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataFilterInterceptor())
                            .addPathPatterns(ruleConfig.getFilterUrl());
    }
}
