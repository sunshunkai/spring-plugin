package com.ssk.web;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-07-15
 */
@RestController
public class HelloController {

    @Autowired
    private RedissonClient redisson;

    @Value("${abc}")
    private String config;

    @RequestMapping("config")
    public String hello(){
        return config;
    }
}
