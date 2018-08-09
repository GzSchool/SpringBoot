package com.eqxuan.peers.controller;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.service.cache.CacheManager;
import com.eqxuan.peers.service.cache.impl.RedisService;
import com.eqxuan.peers.service.impl.TestRedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: redis测试类
 */
@RestController
@RequestMapping("/redis")
public class TestRedisController {

    @Autowired
    TestRedisServiceImpl testRedisService;

    @Autowired
    RedisService redisService;

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("/cacheEmptyUserCard")
    public String cacheEmptyUserCard(String openId) {
        try {
            cacheManager.cacheEmptyUserCard(openId);
            return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/getUserCard")
    public String getUserCard(String openId) {
        try {
            UserCard userCard = cacheManager.getUserCardByOpenId(openId);
            System.out.println(userCard);
            return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/cacheUserCard")
    public String cacheUserCard(String openId) {
        try {
            UserCard userCard = new UserCard();
            userCard.setOpenId(openId);
            userCard.setCtTime(new Date());
            userCard.setUsername("张三");
            cacheManager.cacheUserCard(userCard);
            return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/saveKey")
    public String saveKey(String key,String value) {
        try {
            redisService.set(key, value);
            return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/getValue")
    public String getValue(String key) {
        try {
            return (String) redisService.get(key);
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/delKey")
    public String delKey(String key) {
        try {
            testRedisService.del(key);
            return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}