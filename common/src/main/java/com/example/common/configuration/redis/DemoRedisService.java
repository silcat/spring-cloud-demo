package com.example.common.configuration.redis;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisCommands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class DemoRedisService implements IRedisService {

    private StringRedisTemplate template;


    @Override
    public void set(String key, String value) {
        template.opsForValue().set(key, value);
    }

    @Override
    public void setExpire(String key, String value, long expire) {
        template.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean setBit(String key, long offset, Boolean value) {
        return template.opsForValue().setBit(key, offset, value);
    }

    @Override
    public void hPutAll(String key, Map<String, String> valueMap) {
        template.opsForHash().putAll(key, valueMap);
    }

    @Override
    public void hPut(String key, String filed, String value) {
        HashOperations<String, String, String> opsHash = template.opsForHash();
        opsHash.put(key, filed, value);
    }

    @Override
    public void hDel(String key, List<String> valueKeyList) {
        template.opsForHash().delete(key, valueKeyList);
    }

    @Override
    public String hGet(String key, String filed) {
//        return (String)template.opsForHash().get(key, filed);
        return template.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.hget(key, filed);
            }
        });
    }

    @Override
    public Map<String, String> hGetAll(String key) {
        return template.execute(new RedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.hgetAll(key);
            }
        });
    }

    @Override
    public long expire(String key, int second) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.expire(key, second);
            }
        });
    }

    @Override
    public boolean getBit(String key, long offset) {
        return template.opsForValue().getBit(key, offset);
    }

    @Override
    public void setex(String key, int second, String value) {
        template.opsForValue().set(key, value, second, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return template.opsForValue().get(key);
    }

    @Override
    public long ttl(String key) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.ttl(key);
            }
        });
    }

    @Override
    public Long del(String key) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.del(key);
            }
        });
    }


    @Override
    public Boolean exists(String key) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.exists(key);
            }
        });
    }


}
