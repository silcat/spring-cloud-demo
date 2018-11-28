package com.example.common.redis;

import java.util.Map;

public interface IBaseRedisService {

    /**
     * 添加元素
     */
    void hmSet(String key, Map<String, String> map);

    /**
     * 获取指定key所有键值对
     */
    Map<String, String> hmGet(String key);

    /**
     * 获取指定域名
     */
    String hGet(String key, String filed);

    /**
     * 哈希表 key 中的域 field 的值加上增量 increment
     */
    long hIncr(String key, String filed, long count);

    long hLen(String key);

    void hSet(String key, String filed, String value);

    /**
     * 删除
     */
    void delKey(String key);

    /**
     * 删除指定域
     */
    Long hDel(String key, String filed);

    /**
     * 获取分布式锁
     */
    Boolean acquireLock(String key, String resource, long expired);

    /**
     * 释放锁
     */
    Long releaseLock(String key, String resource);

    void setExpire(String key, String value, long expire);

    void expire(String key, long seconds);

    String get(String key);

    String getSet(String key, String value);


    Long lPush(String listName, String value);

    String rPop(String listName);

}
