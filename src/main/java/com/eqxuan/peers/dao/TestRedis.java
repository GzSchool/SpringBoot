package com.eqxuan.peers.dao;

import java.io.Serializable;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 测试redis
 */
public class TestRedis implements Serializable {

    private String id;

    private String name;

    private Integer age;

    public TestRedis() {
        super();
    }

    public TestRedis(String id, String name, Integer age) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
