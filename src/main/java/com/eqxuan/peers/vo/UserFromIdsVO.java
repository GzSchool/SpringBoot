package com.eqxuan.peers.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author: zheng guangjing.
 * @date: 2018/8/14 16:48
 * @description: 获取表单ID
 */
@Setter
@Getter
@ToString
public class UserFromIdsVO {

    private String openId;

    private List<String> fromIds;
}
