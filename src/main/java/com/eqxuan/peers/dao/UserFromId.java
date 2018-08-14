package com.eqxuan.peers.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author: zheng guangjing.
 * @date: 2018/8/14 16:07
 * @description: 收集当前用户fromId，用于消息模板推送
 */
@Getter
@Setter
@ToString
public class UserFromId {

    /** 用户标识.*/
    private String openId;

    /** 表单ID.*/
    private String fromId;

    /** 表单ID使用状态（1-未使用， 2-已使用 ：默认未使用）.*/
    private Integer status;

    /** 创建时间.*/
    private Date ctTime;
}
