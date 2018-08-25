package com.eqxuan.peers.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author: Mature
 * @Description: 群名片
 * @date 2018/8/25 17:56
 */
@Getter
@Setter
@ToString
public class GroupCard {

	/** 群组ID.*/
	private String groupId;

	/** 用户标识.*/
	private String openId;

	/** 用户卡片ID.*/
	private Integer cardId;

	private Date ctTime;
}
