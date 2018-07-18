package com.zimuka.peers.dao;

import java.util.Date;

/**
 * 用户表
 */
public class User {

    /* 用户ID */
    private Integer id;

    /* 用户标识 */
    private String openid;

    /* 用户名称 */
    private String username;

    /* 创建时间 */
    private Date createTime;

    /* 修改时间 */
    private Date upTime;

    /* 预留字段 */
    private String prepare;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public String getPrepare() {
        return prepare;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }
}
