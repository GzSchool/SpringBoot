package com.zimuka.peers.dto;

/**
 * 返回用户有关同行名片信息
 */
public class UserPeerDTO {

    /* 同行名称 */
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
