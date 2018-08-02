package com.zimuka.peers.service.cache;

import com.zimuka.peers.dao.UserCard;

/**
 * @Auther: Mature
 * @Date: 2018/8/1 16:28
 * @Description: 缓存管理接口
 */
public interface CacheManager {

    boolean cacheEmptyUserCard(String openId);

    UserCard getUserCardByOpenId(String openId);

    boolean cacheUserCard(UserCard userCard);
}
