package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.dto.PageDTO;
import com.zimuka.peers.dto.ReturnGroupDTO;

import java.util.List;

public interface UserGroupService {

    void saveOrUpdate(UserGroup userGroup);

    PageDTO findCardsOnGroupByOpenId(String openId, String groupId, Integer pageNum, Integer pageSize);

    List<ReturnGroupDTO> findUserGroupByParam(UserGroup userGroup);
}
