package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserCard;

import java.util.List;

public interface UserCardService {

    void saveOrUpdate(UserCard userCard);

    UserCard findOneByUser(String openId);

    List<UserCard> findUserCardByParam(UserCard userCard);
}
