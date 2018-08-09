package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dao.UserPeer;
import com.eqxuan.peers.dto.ReturnCardDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPeerMapper {

    int save(UserPeer userPeer);

    int update(UserPeer userPeer);

    UserPeer findOneById(@Param("openId") String openId,
                         @Param("cardId") Integer cardId);

    List<UserPeer> findUserPeerByParam(UserPeer userPeer);

    List<UserCard> findAllByOpenId(String openId);

    List<ReturnCardDTO> findAllPeerByOpenId(String openId);

    UserPeer findOne(@Param("openId") String openId,
                     @Param("cardId") Integer cardId);
}