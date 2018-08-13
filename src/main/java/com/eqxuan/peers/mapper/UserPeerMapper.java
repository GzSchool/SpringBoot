package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.UserPeer;
import com.eqxuan.peers.dto.ReturnCardDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPeerMapper {

    /**
     * 接口描述：保存同行信息
     * @param userPeer
     * @return
     */
    int save(UserPeer userPeer);

    /**
     * 接口描述：修改同行信息
     * @param userPeer
     * @return
     */
    int update(UserPeer userPeer);

    /**
     * 接口描述：查询同行列表
     * @param openId
     * @return
     */
    List<ReturnCardDTO> findAllPeerByOpenId(String openId);

    /**
     * 接口描述：查询单个同行信息
     * @param openId
     * @param cardId
     * @return
     */
    UserPeer findOne(@Param("openId") String openId,
                     @Param("cardId") Integer cardId);

    /**
     * 接口描述：返回当前同行信息详情
     * @param openId
     * @param cardId
     * @return
     */
    ReturnCardDTO getPeerInfo(@Param("openId") String openId,
                              @Param("cardId") Integer cardId);
}
