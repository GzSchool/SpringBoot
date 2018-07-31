package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dto.PageDTO;

import java.util.List;

public interface UserCardService {

    void saveOrUpdate(UserCard userCard);

    UserCard findOneByOpenId(String openId);

    List<UserCard> findCardByParam(UserCard userCard);

    PageDTO findAllByParam(String param, Integer pageNum, Integer pageSize);
}
