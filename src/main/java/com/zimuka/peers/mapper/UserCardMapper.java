package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dto.UserCardDTO;

import java.util.List;

public interface UserCardMapper {

    int save(UserCard userCard);

    int updateCardByUser(UserCard userCard);

    UserCardDTO findOneById(Integer userId);

    List<UserCard> findCardByParam(UserCard userCard);
}
