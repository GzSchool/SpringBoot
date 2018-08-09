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

    private String openId;

    private Integer cardId;

    private String groupId;

    private Integer shareFlag;

    private Integer saveFlag;

    private Integer delFlag;

    private Date ctTime;

    private Date upTime;

    private String prepare;

}
