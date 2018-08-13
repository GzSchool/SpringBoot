package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.UserPeer;
import com.eqxuan.peers.dto.ReturnCardDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPeerMapper {

    int save(UserPeer userPeer);

    int update(UserPeer userPeer);

    List<ReturnCardDTO> findAllPeerByOpenId(String openId);

    UserPeer findOne(@Param("openId") String openId,
                     @Param("cardId") Integer cardId);

    ReturnCardDTO getPeerInfo(@Param("openId") String openId,
                              @Param("cardId") Integer cardId);
}
