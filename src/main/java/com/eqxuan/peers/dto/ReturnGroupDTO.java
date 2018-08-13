package com.eqxuan.peers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 返回前端 指定 群信息 属性
 */
@Getter
@Setter
public class ReturnGroupDTO {

    private String groupId;

    private String openId;

    private Date ctTime;

    private Date upTime;

    private Integer saveFalse;

    private Integer saveTrue;

    private List<String> beforeNineImg;

    private Integer hint;

}
