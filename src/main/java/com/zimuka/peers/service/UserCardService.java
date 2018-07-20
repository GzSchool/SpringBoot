package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dto.UserCardDTO;

import java.util.List;

public interface UserCardService {

    void saveOrUpdate(UserCard userCard);

    UserCardDTO findOneByUser(String openId);

    List<UserCard> findUserCardByParam(UserCard userCard);
}
