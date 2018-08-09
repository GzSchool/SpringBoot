package com.eqxuan.peers.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 修改同行信息操作：前端参数封装
 */
@Getter
@Setter
public class CreatePeersVO {

    private String openId;

    private List<Integer> cardIds;

    private Integer saveFlag;

    private String groupId;

    private String saveName;

    private String fromId;

}
