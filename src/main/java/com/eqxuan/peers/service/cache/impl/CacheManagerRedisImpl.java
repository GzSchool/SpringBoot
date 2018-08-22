package com.eqxuan.peers.service.cache.impl;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.service.cache.CacheManager;
import com.eqxuan.peers.vo.CreatePeersVO;
import com.eqxuan.peers.mapper.UserCardMapper;
import com.eqxuan.peers.mapper.UserPeerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Mature
 * @Date: 2018/8/1 16:35
 * @Description:  Redis实现的缓存
 */
@Service
public class CacheManagerRedisImpl implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(CacheManagerRedisImpl.class);

    private static String PREFIX_USERCARD = "userCard_";

    /** 缓存3分钟.*/
    private static long EMPTY_TIME = 60 * 3;

    /** 缓存一周.*/
    private static long WEEK_SECONDS = 60 * 60 * 24 * 7;

    /** 缓存一小时.*/
    private static long HOUR_SECONDS = 60 * 60;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserCardMapper userCardMapper;

    @Override
    public UserCard getUserCardById(String id) {
        boolean isCache = redisService.exists(PREFIX_USERCARD + id);
        UserCard userCard = null;
        if(isCache){
            userCard = (UserCard) redisService.get(PREFIX_USERCARD + id);
        }else{
            userCard = userCardMapper.findById(Integer.parseInt(id));
            if(userCard != null){
                cacheUserCard(userCard);
            }
        }
        return userCard;
    }

    @Override
    public boolean cacheUserCard(UserCard userCard) {
        return redisService.set(PREFIX_USERCARD + userCard.getId(), userCard, WEEK_SECONDS);
    }

}
