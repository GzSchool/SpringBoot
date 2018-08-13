package com.eqxuan.peers.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 返回前端 指定 名片信息 属性
 */
@Getter
@Setter
public class ReturnCardDTO implements Serializable {

    private Integer id;

    private String openId;

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

    private Integer cardType;

    private Integer saveFlag;

    private String prepare;

    /**
     * 个人主页
     */
    private String homePage;

    /**
     * 公司主页
     */
    private String companyPage;

    private String remark;

}
