package com.zimuka.peers.dao;

import java.util.Date;

/**
 * 用户表
 */
public class User {

    /* 用户ID */
    private Integer id;

    /* 用户openId */
    private String openid;

    /* 会话密钥 */
    private String sessionKey;

    /* 用户标识（openId + sessionKey后加密处理） */
    private String openSession;

    /* 用户名称 */
    private String username;

    /* 创建时间 */
    private Date createTime;

    /* 修改时间 */
    private Date upTime;

    /* 预留字段 */
    private String prepare;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenSession() {
        return openSession;
    }

    public void setOpenSession(String openSession) {
        this.openSession = openSession;
    }

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
