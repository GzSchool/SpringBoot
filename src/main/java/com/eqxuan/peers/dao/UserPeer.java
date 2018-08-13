package com.eqxuan.peers.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 用户关联同行信息
 */
@Getter
@Setter
@ToString
public class UserPeer {

    private Integer id;

    /** 用户标识.*/
    private String openId;

    /** 名片ID.*/
    private Integer cardId;

    /** 群组ID.*/
    private String groupId;

    /** 分享标志(1-个人分享，2-群分享).*/
    private Integer shareFlag;

    /** 保存状态(1-未保存，2-保存，默认未保存).*/
    private Integer saveFlag;

    /** 删除标识(1-未删除，2-已删除，默认未删除).*/
    private Integer delFlag;

    private Date ctTime;

    private Date upTime;

    private String prepare;

    /**
     * 同行姓名备注
     */
    private String remark;

}
