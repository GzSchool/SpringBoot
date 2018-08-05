package com.zimuka.peers.service.cache.impl;

import com.alibaba.fastjson.JSON;
import com.zimuka.peers.controller.UserCardController;
import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dto.ReturnCardDTO;
import com.zimuka.peers.mapper.UserCardMapper;
import com.zimuka.peers.mapper.UserPeerMapper;
import com.zimuka.peers.service.cache.CacheManager;
import com.zimuka.peers.vo.CreatePeersVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @Auther: Mature
 * @Date: 2018/8/1 16:35
 * @Description:  Redis实现的缓存
 */
@Service
public class CacheManagerRedisImpl implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(CacheManagerRedisImpl.class);

    private static String PREFIX_USERCARD = "userCard_";

    private static String PREFIX_PEERLIST = "peerList_";

    private static long EMPTY_TIME = 60 * 3;    //缓存3分钟

    private static long WEEK_SECONDS = 60 * 60 * 24 * 7;   //缓存一周

    private static long HOUR_SECONDS = 60 * 60;   //缓存一小时

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserCardMapper userCardMapper;

    @Autowired
    private UserPeerMapper userPeerMapper;

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
            if(userCard != null){
                cacheUserCard(userCard);
            }
        }
        return userCard;
    }

    @Override
    public boolean cacheUserCard(UserCard userCard) {
        return redisService.set(PREFIX_USERCARD + userCard.getOpenId(), userCard, WEEK_SECONDS);
    }

   /**
    *
    * 功能描述: 根据openId 获取同行列表
    *
    * @param:
    * @return: 
    * @auther: Mature
    * @date:   
    */
    @Override
    public List<ReturnCardDTO> findPeerListByOpenId(String openId) {
        boolean isCache = redisService.exists(PREFIX_PEERLIST + openId);
        List<ReturnCardDTO> peerList = null;
        if(isCache){
            List<String> strList = new ArrayList<String>(redisService.rangeAllPeerByNameScore(PREFIX_PEERLIST + openId));
                for (String str: strList) {
                    ReturnCardDTO returnCardDTO = JSON.parseObject(str, ReturnCardDTO.class);
                    peerList.add(returnCardDTO);
                }
        }else{
            peerList = userPeerMapper.findAllPeerByOpenId(openId);
            if(peerList != null && peerList.size() > 0){
                for (ReturnCardDTO userCard : peerList) {
                    redisService.zAdd(PREFIX_PEERLIST + openId, JSON.toJSONString(userCard), countScoreByName(userCard.getPrepare()));
                }
                redisService.expire(PREFIX_PEERLIST + openId, HOUR_SECONDS);
            }
        }
        return peerList;
    }

    /**
     *
     * 功能描述: 更新同行列表缓存
     *
     * @param:
     * @return:
     * @auther: Mature
     * @date:
     */
    @Override
    public boolean updateCachePeerList(CreatePeersVO peer) {
        boolean result = true;
        try {
            List<Integer> cardIdList = peer.getCardIds();
            int saveFlag = peer.getSaveFlag();
            if(cardIdList != null && cardIdList.size() > 0){
                for (int cardId : cardIdList) {
                    UserCard userCard = userCardMapper.findById(cardId);
                    ReturnCardDTO returnCardDTO = new ReturnCardDTO();
                    BeanUtils.copyProperties(userCard, returnCardDTO);
                    if(saveFlag == 1){  //删除
                        redisService.zRemove(PREFIX_PEERLIST + peer.getOpenId(), returnCardDTO);
                    }else if(saveFlag == 2){    //添加
                        redisService.zAdd(PREFIX_PEERLIST + peer.getOpenId(), JSON.toJSONString(returnCardDTO), countScoreByName(userCard.getPrepare()));
                    }
                }
                redisService.expire(PREFIX_PEERLIST + peer.getOpenId(), HOUR_SECONDS);
            }else{
                logger.info("同行列表缓存无需更新");
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     *
     * 功能描述: 根据名称拼写获取分数
     *
     * @param: username
     * @return:
     * @auther: Mature
     * @date:
     */
    private double countScoreByName(String username) {
        if(username == null){
            return 0;
        }

        if(username.length() > 5){
            username = username.substring(0, 7);
        }

        char[] strs = username.toCharArray();
        StringBuilder sb = new StringBuilder("0");
        for (char str : strs) {
            sb.append(Integer.valueOf(str));
        }
        return Double.parseDouble(sb.toString());
    }

}
