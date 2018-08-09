package com.eqxuan.peers.service;

import com.eqxuan.peers.dao.UserGroup;
import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.dto.ReturnGroupDTO;

import java.util.List;

public interface UserGroupService {

    String saveOrUpdate(UserGroup userGroup);

    PageDTO findCardsOnGroupByOpenId(String openId, String groupId, Integer pageNum, Integer pageSize);

    List<ReturnGroupDTO> findUserGroupByParam(UserGroup userGroup);

    List<ReturnCardDTO> findAllGroupCardByParam(String groupId, String openId, String param);

}
