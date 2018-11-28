//package com.example.common.redis;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisCommands;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@Service
//@ConditionalOnBean(RedisTemplate.class)
//public class BaseRedisService implements IBaseRedisService {
//
//
//    @Autowired
//    private RedisTemplate<String, String> template;
//
//    @Override
//    public void hmSet(String key, Map<String, String> map) {
//        template.opsForHash().putAll(key, map);
//    }
//
//    @Override
//    public Map<String, String> hmGet(String key) {
//        return template.execute((RedisConnection connection) -> {
//                        JedisCommands commands = (JedisCommands) connection.getNativeConnection();
//                        return commands.hgetAll(key);
//        });
//    }
//
//    @Override
//    public String hGet(String key, String filed) {
//        return template.execute((RedisConnection connection) -> {
//            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
//            return commands.hget(key, filed);
//        });
//    }
//
//    @Override
//    public long hIncr(String key, String filed, long count) {
//        return template.execute((RedisConnection connection) -> {
//            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
//            return commands.hincrBy(key, filed, count);
//        });
//    }
//
//    @Override
//    public long hLen(String key) {
//        return template.execute((RedisConnection connection) -> {
//            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
//            return commands.hlen(key);
//        });
//    }
//
//    @Override
//    public void hSet(String key, String filed, String value) {
//        template.opsForHash().put(key, filed, value);
//    }
//
//    @Override
//    public void delKey(String key) {
//        template.delete(key);
//    }
//
//    @Override
//    public Long hDel(String key, String filed) {
//        return template.execute((RedisConnection connection) -> {
//            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
//            return commands.hdel(key, filed);
//        });
//    }
//
//    @Override
//    public Boolean acquireLock(String key, String resource, long expired) {
//        // expired 单位为秒
//        long value = expired * 1000;
//        // 加锁
//        String result = template.execute(new RedisCallback<String>() {
//            @Override
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
//                return commands.set(key, resource, RedisConstant.SET_IF_NOT_EXIST,
//                        RedisConstant.SET_WITH_EXPIRE_TIME, value);
//            }
//        });
//        if (RedisConstant.LOCK_SUCCESS.equals(result)) {
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;
//    }
//
//    @Override
//    public Long releaseLock(String key, String resource) {
//        List<String> keys = new ArrayList<>();
//        keys.add(key);
//        List<String> resources = new ArrayList<>();
//        resources.add(resource);
//        // 调用lua脚本去删除，只能删除自己添加的分布式锁
//        return template.execute(new RedisCallback<Long>() {
//            @Override
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                Object nativeConnection = connection.getNativeConnection();
//                if (nativeConnection instanceof JedisCluster) {
//                    return (Long) ((JedisCluster) nativeConnection).eval(RedisConstant.UNLOCK_LUA, keys, resources);
//                } else if (nativeConnection instanceof Jedis) {
//                    return (Long) ((Jedis) nativeConnection).eval(RedisConstant.UNLOCK_LUA, keys, resources);
//                }
//                return 0L;
//            }
//        });
//    }
//
//    @Override
//    public void setExpire(String key, String value, long expire) {
//        template.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public void expire(String key, long seconds) {
//        template.expire(key, seconds, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public String get(String key) {
//        return template.opsForValue().get(key);
//    }
//
//    @Override
//    public String getSet(String key, String value) {
//        return template.opsForValue().getAndSet(key, value);
//    }
//
//
//    @Override
//    public Long lPush(String listName, String value) {
//        return template.execute(new RedisCallback<Long>() {
//            @Override
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
//                return commands.lpush(listName,value);
//            }
//        });
//    }
//
//    @Override
//    public String rPop(String listName) {
//        return template.execute(new RedisCallback<String>() {
//            @Override
//            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                JedisCommands commands = (JedisCommands) redisConnection.getNativeConnection();
//                return commands.rpop(listName);
//            }
//        });
//    }
//}
