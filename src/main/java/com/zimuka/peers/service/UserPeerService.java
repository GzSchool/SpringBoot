package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dao.UserPeer;

import java.util.List;

public interface UserPeerService {

    void saveOrUpdate(UserPeer userPeer);

    List<UserCard> findAllByOpenId(String openId);
}
