package com.zimuka.peers.dao;

import java.util.Date;

/**
 * 用户群组关联表
 */
public class UserGroup {
    /* 群组ID */
    private String groupId;

    /* 用户ID */
    private Integer userId;

    /* 小程序ID */
    private String appId;

    /* 删除标识（1-未删除，2-已删除）默认未删除 */
    private Integer delFlag;

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
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
