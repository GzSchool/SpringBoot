package com.zimuka.peers.service.impl;

import com.zimuka.peers.dao.User;
import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.enums.PeerShareFlagEnum;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.mapper.UserPeerMapper;
import com.zimuka.peers.service.UserPeerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserPeerServiceImpl implements UserPeerService {

    @Resource
    private UserPeerMapper userPeerMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public void saveOrUpdate(UserPeer userPeer) {
        if (StringUtils.isEmpty(userPeer.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        User checkUser = userMapper.findOneByOpenId(userPeer.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        UserPeer checkUserPeer = userPeerMapper.findOneById(userPeer.getOpenId(), userPeer.getCardId());
        UserPeer saveUserPeer = new UserPeer();
        int rows;

        if (null == checkUserPeer) {
            userPeer.setCtTime(new Date());
            userPeer.setShareFlag(PeerShareFlagEnum.FLAG_BY_PERSON.getKey());
            userPeer.setGroupId("0");//个人分享时，群ID为0
            BeanUtils.copyProperties(userPeer, saveUserPeer);
            rows = userPeerMapper.save(saveUserPeer);
            if (1 != rows) {
                throw new PeerProjectException("保存名片失败");
            }
        } else {
            userPeer.setUpTime(new Date());
            userPeer.setShareFlag(PeerShareFlagEnum.FLAG_BY_PERSON.getKey());
            userPeer.setGroupId("0");
            BeanUtils.copyProperties(userPeer, saveUserPeer);
            rows = userPeerMapper.update(saveUserPeer);
            if (1 != rows) {
                throw new PeerProjectException("修改保存名片失败");
            }
        }
    }

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
}
