package com.eqxuan.peers.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 用户基本属性
 */
@Getter
@Setter
@ToString
public class User {

    private Integer id;

    /** 用户唯一标识.*/
    private String openId;

    /** 小程序会话密钥.*/
    private String sessionKey;

    /** 标识 + 密钥加密后字段.*/
    private String openSession;

    private Date ctTime;

    private Date upTime;

    private String prepare;

}
