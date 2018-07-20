package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dao.UserPeer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPeerMapper {

    int save(UserPeer userPeer);

    int update(UserPeer userPeer);

    UserPeer findOneById(@Param("openId") String openId,
                         @Param("cardId") Integer cardId);

    List<UserPeer> findUserPeerByParam(UserPeer userPeer);

    List<UserCard> findAllByOpenId(String openId);
}
