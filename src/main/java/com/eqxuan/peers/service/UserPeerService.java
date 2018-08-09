package com.eqxuan.peers.service;

import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.vo.CreatePeersVO;

import java.util.List;

public interface UserPeerService {

    //void saveOrUpdate(UserPeer userPeer);

    PageDTO findAllByOpenId(String openId, Integer pageNum, Integer pageSize);

    void saveOrUpdate(CreatePeersVO createPeersVO);

    List<ReturnCardDTO> findAllPeerByOpenId(String openId);

    boolean checkSave(String openId, String cardId);
}
