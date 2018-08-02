package com.zimuka.peers.service.cache.impl;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.mapper.UserCardMapper;
import com.zimuka.peers.service.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: Mature
 * @Date: 2018/8/1 16:35
 * @Description:  Redis实现的缓存
 */
@Service
public class CacheManagerRedisImpl implements CacheManager {

    private static String PREFIX_USERCARD = "userCard_";

    private static String PREFIX_PEERLIST = "peerList_";

    private static long EMPTY_TIME = 60 * 3;    //缓存3分钟

    private static long WEEK_TIME = 60 * 60 * 24 * 7;   //缓存一周

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserCardMapper userCardMapper;

    @Override
    public boolean cacheEmptyUserCard(String openId) {
        return redisService.set(PREFIX_USERCARD + openId, null, EMPTY_TIME);
    }

    @Override
    public UserCard getUserCardByOpenId(String openId) {
        boolean isCache = redisService.exists(PREFIX_USERCARD + openId);
        UserCard userCard = null;
        if(isCache){
            userCard = (UserCard) redisService.get(PREFIX_USERCARD + openId);
        }else{
            userCard = userCardMapper.findOneByOpenId(openId);
        }
        return userCard;
    }

    @Override
    public boolean cacheUserCard(UserCard userCard) {
        return redisService.set(PREFIX_USERCARD + userCard.getOpenId(), userCard, WEEK_TIME);
    }


}
