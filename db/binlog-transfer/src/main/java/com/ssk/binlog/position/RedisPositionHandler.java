package com.ssk.binlog.position;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssk.binlog.BinlogException;
import com.ssk.binlog.config.RedisConfig;
import com.ssk.binlog.config.SyncConfig;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 惊云
 * @date 2021/12/12 11:25
 */
public class RedisPositionHandler implements IPositionHandler{

    private RedisConfig redisConfig;
    private Jedis jedis;
    private JedisPool jedisPool;

    public RedisPositionHandler(RedisConfig redisConfig) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        if (!StringUtils.isBlank(redisConfig.getAuth())) {
            jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(), 1000, redisConfig.getAuth());
        } else {
            jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(), 1000);
        }
    }

    @Override
    public BinlogPositionEntity getPosition(SyncConfig syncConfig) throws BinlogException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String position = jedis.get(syncConfig.getHost() + ":" + syncConfig.getPort());
            if (position != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(position, BinlogPositionEntity.class);
            }
        } catch (JsonProcessingException e) {
            return null;
        } finally {
            if (jedis != null) jedis.close();
        }
        return null;
    }

    @Override
    public void savePosition(SyncConfig syncConfig, BinlogPositionEntity binlogPositionEntity) throws BinlogException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            ObjectMapper objectMapper = new ObjectMapper();
            jedis.set(syncConfig.getHost() + ":" + syncConfig.getPort(), objectMapper.writeValueAsString(binlogPositionEntity));
        } catch (JsonProcessingException e) {
            throw new BinlogException("save position error!" + binlogPositionEntity.toString(), e);
        } finally {
            if (jedis != null) jedis.close();
        }
    }
}
