package com.zimuka.peers.dao;

import java.util.Date;

/**
 * 用户名片表
 */
public class UserCard {

    /* 名片ID */
    private Integer cardId;

    /* 群组ID */
    private String groupId;

    /* 用户ID*/
    private Integer userId;

    /* 小程序ID*/
    private String appId;

    /* 用户头像地址*/
    private String img;

    /* 用户微信号*/
    private String wechatNum;

    /* 公司名称*/
    private String company;

    /* 行业名称*/
    private  String industry;

    /* 城市*/
    private String city;

    /* 电话*/
    private String phone;

    /* 邮箱*/
    private String email;

    /* 简介*/
    private String synopsis;

    /* 需求*/
    private String demand;

    /* 资源*/
    private String resources;

    /* 点赞次数*/
    private Integer giveLikeNum;

    /* 删除标识（1-可用，2-不可用，默认可用）*/
    private Integer delFlag;

    /* 创建时间*/
    private Date createTime;

    /* 修改时间*/
    private  Date upTime;

    /* 预留字段*/
    private String prepare;

    private String openId;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWechatNum() {
        return wechatNum;
    }

    public void setWechatNum(String wechatNum) {
        this.wechatNum = wechatNum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public Integer getGiveLikeNum() {
        return giveLikeNum;
    }

    public void setGiveLikeNum(Integer giveLikeNum) {
        this.giveLikeNum = giveLikeNum;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
