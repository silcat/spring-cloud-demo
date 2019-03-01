package com.example.common.configuration.redis;

import java.util.List;
import java.util.Map;

public interface IRedisService {
    void set(String key, String value);
    void setExpire(String key, String value, long expire);
    void hPutAll(String key, Map<String, String> valueMap);
    void hPut(String key, String filed, String value);
    void hDel(String key, List<String> valueKeyList);
    String hGet(String key, String filed);
    Map<String, String> hGetAll(String key);
    boolean setBit(String key, long offset, Boolean value);
    long expire(String key, int time);
    boolean getBit(String key, long offset);
    void setex(String key, int seconds, String value);
    String get(String key);
    long ttl(String key);
    Long del(String key);
    Boolean exists(String key);

}
