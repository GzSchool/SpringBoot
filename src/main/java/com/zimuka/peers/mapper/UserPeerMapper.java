package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.dto.UserPeerDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPeerMapper {

    List<UserPeerDTO> findAllById(Integer userId);

    List<UserPeerDTO> findAllByParam(String param);

    int savePeerCard(UserPeer userPeer);

    int updateByUserId(UserPeer userPeer);

    UserPeer findOneById(@Param("userId") Integer userId,
                         @Param("peerCardId") Integer peerCardId);

    List<UserPeer> findUserPeerByParam(UserPeer userPeer);
}
