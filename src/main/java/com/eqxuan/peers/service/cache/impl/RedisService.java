package com.eqxuan.peers.service.cache.impl;

import com.eqxuan.peers.dto.ReturnCardDTO;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Mature
 * @Date: 2018/8/1 16:20
 * @Description: Redis缓存操作类
 */
@Service
public class RedisService {

    private static final double MAX_SCORE = 9007199254740992D;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<Serializable, Object> simpleValOps;

    @Resource(name = "redisTemplate")
    ZSetOperations<Serializable, ReturnCardDTO> zSetOperations;

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            simpleValOps.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            simpleValOps.set(key, value);
            result = redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        result = simpleValOps.get(key);
        return result;
    }

    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPush(k,v);
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param score
     */
    public boolean zAdd(String key, ReturnCardDTO value, double score){
        boolean result = false;
        try {
            result = zSetOperations.add(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: 删除同行缓存
     *
     * @auther: Mature
     */
    public boolean zRemove(String key,ReturnCardDTO value){
        boolean result = true;
        try {
            long unm = zSetOperations.remove(key, value);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public Set<ReturnCardDTO> rangePeerListByScore(String key, double scoure, double scoure1){
        return zSetOperations.rangeByScore(key, scoure, scoure1);
    }

    /**
     * @Description: 获取同行列表缓存
     *
     * @auther: Mature
     */
    public Set<ReturnCardDTO> rangeAllPeerByNameScore(String key){
        return zSetOperations.rangeByScore(key, 0, MAX_SCORE);
    }

    /**
     * @Description: 设置过期时间
     *
     * @auther: Mature
     */
    public boolean expire(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }
}
