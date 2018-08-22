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
     * 接口描述：获取当前用户名片信息
     * @param id
     * @return
     */
    UserCard getUserCardById(String id);

    /**
     * 接口描述：缓存当前用户名片信息
     * @param userCard
     * @return
     */
    boolean cacheUserCard(UserCard userCard);

}
