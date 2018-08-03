package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dto.ReturnCardDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCardMapper {

    int save(UserCard userCard);

    int update(UserCard userCard);

    UserCard findOneByOpenId(String openId);

    List<UserCard> findCardByParam(UserCard userCard);

    List<UserCard> findAllByParam(String param);

    List<ReturnCardDTO> findAllByPeerAndParam(@Param("param") String param,
                                              @Param("openId") String openId);
}
