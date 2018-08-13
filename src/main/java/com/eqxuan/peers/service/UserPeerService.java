package com.eqxuan.peers.service;

import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.vo.CreatePeersVO;

import java.util.List;

public interface UserPeerService {

    /**
     * 接口描述：保存或修改同行信息
     * @param createPeersVO
     */
    void saveOrUpdate(CreatePeersVO createPeersVO);

    /**
     * 接口描述：查询同行列表
     * @param openId
     * @return
     */
    List<ReturnCardDTO> findAllPeerByOpenId(String openId);

    /**
     * 接口描述：查询当前名片是否被当前用户保存
     * @param openId
     * @param cardId
     * @return
     */
    boolean checkSave(String openId, String cardId);

    /**
     * 接口描述：添加备注
     * @param openId 当前用户
     * @param cardId 名片ID
     * @param remark 备注
     * @return
     */
    boolean addRemark(String openId, String cardId, String remark);

    /**
     * 接口描述：查询单个同行信息详情
     * @param openId
     * @param cardId
     * @return
     */
    ReturnCardDTO getPeerInfo(String openId, String cardId);
}
