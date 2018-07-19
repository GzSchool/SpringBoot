package com.zimuka.peers.service.impl;

import com.zimuka.peers.dao.User;
import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.dto.UserPeerDTO;
import com.zimuka.peers.enums.PeerCardSaveFlagEnum;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.mapper.UserPeerMapper;
import com.zimuka.peers.service.UserPeerService;
import com.zimuka.peers.vo.UserPeerSaveVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserPeerServiceImpl implements UserPeerService {

    @Resource
    private UserPeerMapper userPeerMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserPeerDTO> findAll(String openId) {

        if (StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("openId不可为空");
        }

        User checkUser = userMapper.findOneByOpenId(openId);

        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        List<UserPeerDTO> userPeerDTOS = userPeerMapper.findAllById(checkUser.getId());

        return userPeerDTOS;
    }

    @Override
    public List<UserPeerDTO> findAllByParam(String param) {
        String checkedParam = param.replaceAll("","");

        List<UserPeerDTO> userPeerDTOS = userPeerMapper.findAllByParam(checkedParam);

        return userPeerDTOS;
    }

    @Override
    public void saveOrUpdate(UserPeerSaveVO userPeerSaveVO) {
        if (StringUtils.isEmpty(userPeerSaveVO.getOpenId())) {
            throw new PeerProjectException("参数不完整，缺少openId");
        }

        User checkUser = userMapper.findOneByOpenId(userPeerSaveVO.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        UserPeer checkUserPeer = userPeerMapper.findOneById(checkUser.getId(), userPeerSaveVO.getPeerCardId());
        int rows;
        UserPeer saveUserPeer;
        if (null == checkUserPeer) {
            saveUserPeer = new UserPeer();
            BeanUtils.copyProperties(userPeerSaveVO, saveUserPeer);
            saveUserPeer.setUserId(checkUser.getId());

            rows = userPeerMapper.savePeerCard(saveUserPeer);
            if (1 != rows) {
                throw new PeerProjectException("保存名片失败");
            }
        } else {
            saveUserPeer = new UserPeer();
            BeanUtils.copyProperties(userPeerSaveVO, saveUserPeer);
            saveUserPeer.setUserId(checkUser.getId());

            rows = userPeerMapper.updateByUserId(saveUserPeer);
            if (1 != rows) {
                throw new PeerProjectException("名片操作失败");
            }

            //如果saveFlag为保存状态，需要对被保存人进行消息模板推送  根据cardId，找到对应的被推送者的openId
            if (userPeerSaveVO.getSaveFlag().intValue() == PeerCardSaveFlagEnum.SAVE_FLAG_TRUE.getKey()) {
                //消息模板推送
            }
        }
    }

    @Override
    public List<UserPeer> findUserPeerByParam(UserPeer userPeer) {

        User checkUser = userMapper.findOneByOpenId(userPeer.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }
        userPeer.setUserId(checkUser.getId());

        List<UserPeer> userPeerList = userPeerMapper.findUserPeerByParam(userPeer);

        return userPeerList;
    }
}
