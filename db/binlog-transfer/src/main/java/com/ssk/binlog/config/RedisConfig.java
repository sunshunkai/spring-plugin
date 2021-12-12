package com.ssk.binlog.config;

import lombok.Data;

/**
 * Redis 连接信息
 * @author 惊云
 * @date 2021/12/12 10:13
 */
@Data
public class RedisConfig {
    private String host;
    private Integer port;
    private String auth;

    public RedisConfig(String host,Integer port){
        this.host = host;
        this.port = port;
    }
}
