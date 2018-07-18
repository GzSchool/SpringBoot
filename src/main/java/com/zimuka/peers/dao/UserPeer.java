package com.zimuka.peers.dao;

import java.util.Date;

/**
 * 用户同行关联表
 */
public class UserPeer {

    /* 用户ID */
    private Integer userId;

    /* 同行名片ID */
    private Integer peerCardId;

    /* 保存状态(1-保存，2-未保存，默认未保存) */
    private Integer saveFlag;

    /* 删除标识(1-未删除，2-已删除) */
    private Integer delFlag;

    /* 创建时间 */
    private Date createTime;

    /* 修改时间 */
    private Date upTime;

    /* 预留字段*/
    private String prepare;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPeerCardId() {
        return peerCardId;
    }

    public void setPeerCardId(Integer peerCardId) {
        this.peerCardId = peerCardId;
    }

    public Integer getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Integer saveFlag) {
        this.saveFlag = saveFlag;
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
}
