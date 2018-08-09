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

    private String formId;

}
