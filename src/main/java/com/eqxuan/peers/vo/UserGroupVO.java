package com.eqxuan.peers.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: zheng guangjing.
 * @date: 2018/8/25 18:28
 * @description: description
 */
@ToString
@Getter
@Setter
public class UserGroupVO {

    /** 当前用户openId.*/
    private String myOpenId;

    /** 分享人的openId.*/
    private String otherOpenId;

    /** 分享名片ID.*/
    private String cardId;

    /** 加密信息.*/
    private String encryptedData;

    /** 偏移量.*/
    private String iv;
}
