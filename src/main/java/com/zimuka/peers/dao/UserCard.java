package com.zimuka.peers.dao;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

public class UserCard implements Serializable {

    private Integer id;

    private String openId;

    //冗余字段，重构时可删除
    private String appId;

    private String username;

    private String userWechat;

    private String userCompany;

    private String userIndustry;

    private String userCity;

    private String userJob;

    private String userImg;

    private String userPhone;

    @Pattern(regexp = "^(.+)@(.+)$", message = "邮箱格式不合法")
    private String userEmail;

    private String synopsis;

    private String demand;

    private String resources;

    private Integer giveLikeNum;

    private Integer delFlag;

    private Date ctTime;

    private Date upTime;

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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserWechat() {
        return userWechat;
    }

    public void setUserWechat(String userWechat) {
        this.userWechat = userWechat;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserIndustry() {
        return userIndustry;
    }

    public void setUserIndustry(String userIndustry) {
        this.userIndustry = userIndustry;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public Date getCtTime() {
        return ctTime;
    }

    public void setCtTime(Date ctTime) {
        this.ctTime = ctTime;
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

    private String formId;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    @Override
    public String toString() {
        return "UserCard{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", appId='" + appId + '\'' +
                ", username='" + username + '\'' +
                ", userWechat='" + userWechat + '\'' +
                ", userCompany='" + userCompany + '\'' +
                ", userIndustry='" + userIndustry + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userJob='" + userJob + '\'' +
                ", userImg='" + userImg + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", demand='" + demand + '\'' +
                ", resources='" + resources + '\'' +
                ", giveLikeNum=" + giveLikeNum +
                ", delFlag=" + delFlag +
                ", ctTime=" + ctTime +
                ", upTime=" + upTime +
                ", prepare='" + prepare + '\'' +
                ", formId='" + formId + '\'' +
                '}';
    }
}
