package com.zimuka.peers.service;

import com.zimuka.peers.dto.UserPeerDTO;
import com.zimuka.peers.vo.UserPeerSaveVO;

import java.util.List;

public interface UserPeerService {

    List<UserPeerDTO> findAll(String openId);

    List<UserPeerDTO> findAllByParam(String param);

    void saveOrUpdate(UserPeerSaveVO userPeerSaveVO);
}
