package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.dto.CardsOnGroupDTO;

import java.util.List;

public interface UserGroupService {

    void saveOrUpdate(UserGroup userGroup);

    List<CardsOnGroupDTO> findCardsOnGroupByOpenId(String openId, String groupId);
}
