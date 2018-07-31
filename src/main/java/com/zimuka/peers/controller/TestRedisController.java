package com.zimuka.peers.controller;

import com.zimuka.peers.service.impl.TestRedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class TestRedisController {

    @Autowired
    TestRedisServiceImpl testRedisService;

    @RequestMapping("/saveKey")
    public String saveKey(String key,String value) {
        try {
            testRedisService.setKey(key, value);
            return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/getValue")
    public String getValue(String key) {
        try {
            return testRedisService.getStr(key);
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
