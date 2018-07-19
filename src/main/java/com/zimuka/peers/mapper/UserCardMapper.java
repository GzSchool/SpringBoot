package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.UserCard;

import java.util.List;

public interface UserCardMapper {

    int save(UserCard userCard);

    int updateCardByUser(UserCard userCard);

    UserCard findOneById(Integer userId);

    List<UserCard> findCardByParam(UserCard userCard);
}
