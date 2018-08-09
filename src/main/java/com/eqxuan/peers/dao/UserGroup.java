package com.eqxuan.peers.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 用户关联微信群
 */
@Getter
@Setter
@ToString
public class UserGroup {

    private String groupId;

    private String openId;

    //冗余字段，重构时可删除
    private String appId;

    private Integer delFlag;

    private Date ctTime;

    private Date upTime;

    private String prepare;

    private String encryptedData;

    private String iv;

    private String otherOpenId;

}
