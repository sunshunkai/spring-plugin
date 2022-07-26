package com.ssk.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-07-15
 */
@Component
@Order(-10000)
public class ConfigDataEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final static String CUSTOM_CONFIG = "CUSTOM_CONFIG";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String,Object> config = new HashMap<>();
        config.put("abc","lisi");
        environment.getPropertySources().addFirst(new MapPropertySource(CUSTOM_CONFIG,config));
    }
}
