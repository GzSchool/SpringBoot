package com.zimuka.peers.dao;

import java.util.Date;

/**
 * 用户群组关联表
 */
public class UserGroup {
    /* 群组ID */
    private String groupId;

    /* 用户ID */
    private Integer UserId;

    /* 小程序ID */
    private String appId;

    /* 创建时间 */
    private Date createTime;

    /* 修改时间 */
    private Date upTime;

    /* 预留字段 */
    private String prepare;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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
