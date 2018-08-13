package com.eqxuan.peers.service.cache;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.vo.CreatePeersVO;

import java.util.List;

/**
 * @Auther: Mature
 * @Date: 2018/8/1 16:28
 * @Description: 缓存管理接口
 */
public interface CacheManager {

    /**
     * 接口描述：默认缓存个人名片信息
     * @param openId
     * @return
     */
    boolean cacheEmptyUserCard(String openId);

    /**
     * 接口描述：获取当前用户名片信息
     * @param openId
     * @return
     */
    UserCard getUserCardByOpenId(String openId);

    /**
     * 接口描述：缓存当前用户名片信息
     * @param userCard
     * @return
     */
    boolean cacheUserCard(UserCard userCard);

    /**
     * 接口描述：根据openId 获取同行列表
     * @param openId
     * @return
     */
    List<ReturnCardDTO> findPeerListByOpenId(String openId);

    /**
     * 接口描述：更新同行列表缓存
     * @param createPeersVO
     * @return
     */
    boolean updateCachePeerList(CreatePeersVO createPeersVO);
}
