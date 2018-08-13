package com.eqxuan.peers.service;

import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.vo.CreatePeersVO;

import java.util.List;

public interface UserPeerService {

    void saveOrUpdate(CreatePeersVO createPeersVO);

    List<ReturnCardDTO> findAllPeerByOpenId(String openId);

    boolean checkSave(String openId, String cardId);

    boolean addRemark(String openId, String cardId, String remark);
}
