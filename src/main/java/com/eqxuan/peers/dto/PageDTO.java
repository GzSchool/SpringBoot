package com.eqxuan.peers.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 分页数据统一返回格式
 */
@Getter
@Setter
@ToString
public class PageDTO {

    private Integer pages;

    private Long total;

    private List<?> result;

    private Integer saveFalseNum;
}
