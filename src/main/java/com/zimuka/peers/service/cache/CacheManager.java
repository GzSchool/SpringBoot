package com.zimuka.peers.service.cache;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dto.ReturnCardDTO;
import com.zimuka.peers.vo.CreatePeersVO;

import java.util.List;

/**
 * @Auther: Mature
 * @Date: 2018/8/1 16:28
 * @Description: 缓存管理接口
 */
public interface CacheManager {

    boolean cacheEmptyUserCard(String openId);

    UserCard getUserCardByOpenId(String openId);

    boolean cacheUserCard(UserCard userCard);

    List<ReturnCardDTO> findPeerListByOpenId(String openId);

    boolean updateCachePeerList(CreatePeersVO createPeersVO);
}
