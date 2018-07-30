package com.zimuka.peers.service.impl;

import com.zimuka.peers.dao.User;
import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.enums.PeerShareFlagEnum;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.mapper.UserPeerMapper;
import com.zimuka.peers.service.UserPeerService;
import com.zimuka.peers.vo.CreatePeersVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserPeerServiceImpl implements UserPeerService {

    private static final Logger logger = LoggerFactory.getLogger(UserPeerServiceImpl.class);

    @Resource
    private UserPeerMapper userPeerMapper;

    @Resource
    private UserMapper userMapper;

//    @Override
//    public void saveOrUpdate(UserPeer userPeer) {
//        if (StringUtils.isEmpty(userPeer.getOpenId())) {
//            throw new PeerProjectException("用户未登陆");
//        }
//
//        User checkUser = userMapper.findOneByOpenId(userPeer.getOpenId());
//        if (null == checkUser) {
//            throw new PeerProjectException("用户未注册");
//        }
//
//        UserPeer checkUserPeer = userPeerMapper.findOneById(userPeer.getOpenId(), userPeer.getCardId());
//        UserPeer saveUserPeer = new UserPeer();
//        int rows;
//
//        if (null == checkUserPeer) {
//            userPeer.setCtTime(new Date());
//            userPeer.setShareFlag(PeerShareFlagEnum.FLAG_BY_PERSON.getKey());
//            userPeer.setGroupId("0");//个人分享时，群ID为0
//            BeanUtils.copyProperties(userPeer, saveUserPeer);
//            rows = userPeerMapper.save(saveUserPeer);
//            if (1 != rows) {
//                throw new PeerProjectException("保存名片失败");
//            }
//        } else {
//            userPeer.setUpTime(new Date());
//            userPeer.setShareFlag(PeerShareFlagEnum.FLAG_BY_PERSON.getKey());
//            userPeer.setGroupId("0");
//            BeanUtils.copyProperties(userPeer, saveUserPeer);
//            rows = userPeerMapper.update(saveUserPeer);
//            if (1 != rows) {
//                throw new PeerProjectException("修改保存名片失败");
//            }
//        }
//    }

    @Override
    public List<UserCard> findAllByOpenId(String openId) {

        if (StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("用户未登陆");
        }

        User checkUser = userMapper.findOneByOpenId(openId);
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        List<UserCard> userCardList = userPeerMapper.findAllByOpenId(openId);
        return userCardList;
    }

    @Override
    public void saveOrUpdate(CreatePeersVO createPeersVO) {

        logger.info("【卡片集合】：{}", createPeersVO.getCardIds());


        if (StringUtils.isEmpty(createPeersVO.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        User checkUser = userMapper.findOneByOpenId(createPeersVO.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        List<Integer> cardIdList = createPeersVO.getCardIds();

        logger.info("【获取卡片集合】：{}", cardIdList);


        int rows;
        UserPeer saveUserPeer = new UserPeer();
        for (int cardId : cardIdList) {
            UserPeer checkUserPeer = userPeerMapper.findOneById(createPeersVO.getOpenId(), cardId);
            saveUserPeer.setCardId(cardId);
            if (createPeersVO.getGroupId().equals("0")) {
                saveUserPeer.setShareFlag(PeerShareFlagEnum.FLAG_BY_PERSON.getKey());
            } else {
                saveUserPeer.setShareFlag(PeerShareFlagEnum.FLAG_BY_GROUP.getKey());
            }
            if (null == checkUserPeer) {
                saveUserPeer.setCtTime(new Date());
                BeanUtils.copyProperties(createPeersVO, saveUserPeer);
                rows = userPeerMapper.save(saveUserPeer);
                if (1 != rows) {
                    throw new PeerProjectException("保存名片失败");
                }
            } else {
                saveUserPeer.setUpTime(new Date());
                BeanUtils.copyProperties(createPeersVO, saveUserPeer);
                rows = userPeerMapper.update(saveUserPeer);
                if (1 != rows) {
                    throw new PeerProjectException("修改保存名片失败");
                }
            }
        }
    }
}
