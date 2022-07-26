package com.ssk.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-07-22
 */
@Configuration
public class Redis {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        // sentinel
        if (redisProperties.getSentinel() != null) {
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
            List<String> nodeList = redisProperties.getSentinel().getNodes();

            List<String> redisList = nodeList.stream()
                    .map(node -> node.startsWith("redis://") ? node : "redis://" + node).collect(Collectors.toList());

            String[] nodes = new String[nodeList.size()];
            sentinelServersConfig.addSentinelAddress(redisList.toArray(nodes));
            sentinelServersConfig.setDatabase(redisProperties.getDatabase());
            if (redisProperties.getPassword() != null) {
                sentinelServersConfig.setPassword(redisProperties.getPassword());
            }
        } else {
            // single server
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress(redisProperties.getHost() + ":" + redisProperties.getPort());
            singleServerConfig.setDatabase(redisProperties.getDatabase());
            if (redisProperties.getPassword() != null) {
                singleServerConfig.setPassword(redisProperties.getPassword());
            }
        }
        return Redisson.create(config);
    }
}
