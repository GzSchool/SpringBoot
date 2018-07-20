package com.zimuka.peers.dao;

import java.util.Date;

public class User {

    private Integer id;

    private String openId;

    private String sessionKey;

    private String openSession;

    private Date ct_time;

    private Date up_time;

    private String prepare;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

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

    public Date getCt_time() {
        return ct_time;
    }

    public void setCt_time(Date ct_time) {
        this.ct_time = ct_time;
    }

    public Date getUp_time() {
        return up_time;
    }

    public void setUp_time(Date up_time) {
        this.up_time = up_time;
    }

    public String getPrepare() {
        return prepare;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }
}
