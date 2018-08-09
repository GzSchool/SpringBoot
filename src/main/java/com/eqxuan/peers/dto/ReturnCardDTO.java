package com.eqxuan.peers.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 返回前端 指定 名片信息 属性
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnCardDTO that = (ReturnCardDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(userWechat, that.userWechat) &&
                Objects.equals(userCompany, that.userCompany) &&
                Objects.equals(userIndustry, that.userIndustry) &&
                Objects.equals(userCity, that.userCity) &&
                Objects.equals(userJob, that.userJob) &&
                Objects.equals(userImg, that.userImg) &&
                Objects.equals(userPhone, that.userPhone) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(synopsis, that.synopsis) &&
                Objects.equals(demand, that.demand) &&
                Objects.equals(resources, that.resources) &&
                Objects.equals(giveLikeNum, that.giveLikeNum) &&
                Objects.equals(saveFlag, that.saveFlag) &&
                Objects.equals(prepare, that.prepare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userWechat, userCompany, userIndustry, userCity, userJob, userImg, userPhone, userEmail, synopsis, demand, resources, giveLikeNum, saveFlag, prepare);
    }
}
