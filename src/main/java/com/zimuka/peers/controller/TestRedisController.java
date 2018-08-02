package com.zimuka.peers.controller;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.service.cache.CacheManager;
import com.zimuka.peers.service.impl.TestRedisServiceImpl;
import com.zimuka.peers.service.cache.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
