package com.eqxuan.peers.service;

import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.dto.ReturnGroupDTO;
import com.eqxuan.peers.vo.UserGroupVO;

import java.util.List;

public interface UserGroupService {

    /**
     * 接口描述：查询群内 除当前用户以外的所有名片 分页（改）
     * @param openId
     * @param groupId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDTO findCardsOnGroupByOpenId(String openId, String groupId, Integer pageNum, Integer pageSize);

    /**
     * 接口描述：搜索群内名片 （改）
     * @param groupId
     * @param openId
     * @param param
     * @return
     */
    List<ReturnCardDTO> findAllGroupCardByParam(String groupId, String openId, String param);

    /**
     * 接口描述：去除红点通知
     * @param groupId
     * @param openId
     * @return
     */
    int removeHint(String groupId, String openId);

    /**
     * 接口描述：用户分享名片到群（新）
     * @param userGroupVO
     * @return
     */
    String save(UserGroupVO userGroupVO);

    /**
     * 接口描述：查询群列表（新）
     * @param openId
     * @param share
     * @return
     */
    List<ReturnGroupDTO> findUserGroupList(String openId, String share);

    /**
     * 接口描述：群内个人名片（新）
     * @param groupId
     * @param openId
     * @return
     */
    List<ReturnCardDTO> getOwnerCardsOnGroup(String groupId, String openId);
}
