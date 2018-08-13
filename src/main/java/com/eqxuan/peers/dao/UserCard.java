package com.eqxuan.peers.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 用户名片基本属性
 */
@Getter
@Setter
@ToString
public class UserCard implements Serializable {

    private Integer id;

    /** 用户标识.*/
    private String openId;

    /** 冗余字段，重构时可删除.*/
    private String appId;

    /** 用户名称.*/
    private String username;

    /** 用户微信号.*/
    private String userWechat;

    /** 用户公司.*/
    private String userCompany;

    /** 用户行业.*/
    private String userIndustry;

    /** 城市.*/
    private String userCity;

    /** 用户职务.*/
    private String userJob;

    /** 用户头像地址.*/
    private String userImg;

    /** 用户电话.*/
    private String userPhone;

    @Pattern(regexp = "^(.+)@(.+)$", message = "邮箱格式不合法")
    private String userEmail;

    /** 简介.*/
    private String synopsis;

    /** 需求.*/
    private String demand;

    /** 资源.*/
    private String resources;

    /** 点赞次数.*/
    private Integer giveLikeNum;

    /** 名片类型.*/
    private Integer cardType;

    /** 删除标识（1-可用，2-不可用，默认可用）.*/
    private Integer delFlag;

    private Date ctTime;

    private Date upTime;

    private String prepare;

    /** 微信表单ID（非表字段）.*/
    private String formId;

    /**
     * 个人主页
     */
    private String homePage;

    /**
     * 公司主页
     */
    private String companyPage;

}
