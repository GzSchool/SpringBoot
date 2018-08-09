package com.eqxuan.peers.service;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.ReturnCardDTO;

import java.util.List;

public interface UserCardService {

    void saveOrUpdate(UserCard userCard);

    UserCard findOneByOpenId(String openId);

    List<UserCard> findCardByParam(UserCard userCard);

    List<ReturnCardDTO> findAllByPeerAndParam(String param, String openId);
}
