package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.ReturnCardDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserCardMapper {

    int save(UserCard userCard);

    int update(UserCard userCard);

    UserCard findOneByOpenId(String openId);

    List<UserCard> findCardByParam(UserCard userCard);

    List<UserCard> findAllByParam(String param);

	UserCard findById(int id);

	List<ReturnCardDTO> findAllByPeerAndParam(@Param("param") String param,
                                              @Param("openId") String openId);}
