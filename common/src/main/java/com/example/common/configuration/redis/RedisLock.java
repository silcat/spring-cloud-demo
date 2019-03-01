package com.example.common.configuration.redis;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RedisLock {

    private StringRedisTemplate template;
  
    public Boolean acquireLock(String key, String resource, long expired) {
        // expired 单位为秒
        long value = expired * 1000;
        // 加锁
        String result = template.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.set(key, resource, RedisConstant.SET_IF_NOT_EXIST, RedisConstant.SET_WITH_EXPIRE_TIME, value);
            }
        });
        if (RedisConstant.LOCK_SUCCESS.equals(result)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Long releaseLock(String key, String resource) {
        List<String> keys = new ArrayList<>();
        keys.add(key);
        List<String> resources = new ArrayList<>();
        resources.add(resource);
        // 调用lua脚本去删除，只能删除自己添加的分布式锁
        return template.execute((RedisConnection connection)->{
            Object nativeConnection = connection.getNativeConnection();
            if (nativeConnection instanceof JedisCluster) {
                return (Long) ((JedisCluster) nativeConnection).eval(RedisConstant.UNLOCK_LUA, keys, resources);
            } else if (nativeConnection instanceof Jedis) {
                return (Long) ((Jedis) nativeConnection).eval(RedisConstant.UNLOCK_LUA, keys, resources);
            }
            return 0L;
        });

    }
}
