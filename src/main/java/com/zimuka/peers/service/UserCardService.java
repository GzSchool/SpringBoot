package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserCard;

public interface UserCardService {

    void saveOrUpdate(UserCard userCard);

    UserCard findOneByUser(String openId);
}
