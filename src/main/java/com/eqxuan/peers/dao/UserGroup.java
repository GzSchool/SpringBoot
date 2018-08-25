package com.eqxuan.peers.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author: zheng guangjing.
 * @Date: 2018/8/13 16:05
 * @Description: 用户关联微信群
 */
@Getter
@Setter
@ToString
public class UserGroup {

    /** 群组ID.*/
    private String groupId;

    /** 用户标识.*/
    private String openId;

    /** 删除标识（1-未删除，2-已删除，默认未删除）.*/
    private Integer delFlag;

    private Date ctTime;

    private Date upTime;

    /** 分享来源（1-本人分享，2-他人分享）.*/
    private String share;

    /** 红点提示信息 1:有提示  0:无提示.*/
    private Integer hint;

}
