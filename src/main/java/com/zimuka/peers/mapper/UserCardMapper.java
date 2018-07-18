package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.UserCard;

public interface UserCardMapper {

    int save(UserCard userCard);

    int updateCardByUser(UserCard userCard);

    UserCard findOneById(Integer userId);
}
