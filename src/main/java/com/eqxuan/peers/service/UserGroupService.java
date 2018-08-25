package com.eqxuan.peers.service;

import com.eqxuan.peers.dao.UserGroup;
import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.dto.ReturnGroupDTO;
import com.eqxuan.peers.vo.UserGroupVO;

import java.util.List;

public interface UserGroupService {

    /**
     * 接口描述：保存或修改群信息
     * @param userGroup
     * @return
     */
    String saveOrUpdate(UserGroup userGroup);

    /**
     * 接口描述：查询群内 除当前用户以外的所有名片（分页）
     * @param openId
     * @param groupId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDTO findCardsOnGroupByOpenId(String openId, String groupId, Integer pageNum, Integer pageSize);

    /**
     * 接口描述：根据入参查询群内名片
     * @param userGroup
     * @return
     */
    List<ReturnGroupDTO> findUserGroupByParam(UserGroup userGroup);

    /**
     * 接口描述：搜索群内名片
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
}
