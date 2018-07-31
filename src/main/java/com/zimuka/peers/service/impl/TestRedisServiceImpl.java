package com.zimuka.peers.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TestRedisServiceImpl {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valOpsStr;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOpsObj;

    /**
     * 根据指定的key获取value
     * @param key
     * @return
     */
    public String getStr(String key) {
        return valOpsStr.get(key);
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    public void setKey(String key, String value) {
        valOpsStr.set(key, value);
    }

    /**
     * 删除指定key
     * @param key
     */
    public void del(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 根据指定Object返回value
     * @param obj
     * @return
     */
    public Object getObj(Object obj) {
        return valOpsObj.get(obj);
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    public void setObj(Object key, Object value) {
        valOpsObj.set(key, value);
    }

    /**
     * 删除缓存
     * @param obj
     */
    public void delObj(Object obj) {
        redisTemplate.delete(obj);
    }
}
