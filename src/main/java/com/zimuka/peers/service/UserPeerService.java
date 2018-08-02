package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.dto.PageDTO;
import com.zimuka.peers.dto.ReturnCardDTO;
import com.zimuka.peers.vo.CreatePeersVO;

import java.util.List;

public interface UserPeerService {

    //void saveOrUpdate(UserPeer userPeer);

    PageDTO findAllByOpenId(String openId, Integer pageNum, Integer pageSize);

    void saveOrUpdate(CreatePeersVO createPeersVO);

    List<ReturnCardDTO> findAllPeerByOpenId(String openId);
}
