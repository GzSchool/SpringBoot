package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserCard;

import java.util.List;

public interface UserCardService {

    void saveOrUpdate(UserCard userCard);

    UserCard findOneByOpenId(String openId);

    List<UserCard> findCardByParam(UserCard userCard);

    List<UserCard> findAllByParam(String param);
}
