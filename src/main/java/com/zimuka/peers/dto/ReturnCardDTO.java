package com.zimuka.peers.dto;

import java.io.Serializable;

public class ReturnCardDTO implements Serializable {

    private Integer id;

    private String username;

    private String userWechat;

    private String userCompany;

    private String userIndustry;

    private String userCity;

    private String userJob;

    private String userImg;

    private String userPhone;

    private String userEmail;

    private String synopsis;

    private String demand;

    private String resources;

    private Integer giveLikeNum;

    private Integer saveFlag;

    private String prepare;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Integer saveFlag) {
        this.saveFlag = saveFlag;
    }

    public String getPrepare() {
        return prepare;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }
}
