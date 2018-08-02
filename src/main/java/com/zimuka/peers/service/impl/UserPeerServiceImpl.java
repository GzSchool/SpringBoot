package com.zimuka.peers.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.dto.PageDTO;
import com.zimuka.peers.dto.ReturnCardDTO;
import com.zimuka.peers.enums.PeerShareFlagEnum;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserPeerMapper;
import com.zimuka.peers.service.UserPeerService;
import com.zimuka.peers.vo.CreatePeersVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserPeerServiceImpl implements UserPeerService {

    @Resource
    private UserPeerMapper userPeerMapper;

//    @Override
//    public void saveOrUpdate(UserPeer userPeer) {
//        if (StringUtils.isEmpty(userPeer.getOpenId())) {
//            throw new PeerProjectException("用户未登陆");
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
    public PageDTO findAllByOpenId(String openId, Integer pageNum, Integer pageSize) {

        if (StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("用户未登陆");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<UserCard> userCardList = userPeerMapper.findAllByOpenId(openId);

        PageInfo<UserCard> pageInfo = new PageInfo<UserCard>(userCardList);
        List<ReturnCardDTO> returnCardDTOS = new ArrayList<ReturnCardDTO>(pageInfo.getList().size());
        for (UserCard userCard : pageInfo.getList()) {
            ReturnCardDTO returnCardDTO = new ReturnCardDTO();
            BeanUtils.copyProperties(userCard, returnCardDTO);
            returnCardDTOS.add(returnCardDTO);
        }

        PageDTO pageDTO = new PageDTO();
        pageDTO.setResult(returnCardDTOS);
        pageDTO.setPages(pageInfo.getPages());
        pageDTO.setTotal(pageInfo.getTotal());

        return pageDTO;
    }

    @Override
    public void saveOrUpdate(CreatePeersVO createPeersVO) {

        if (StringUtils.isEmpty(createPeersVO.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        List<Integer> cardIdList = createPeersVO.getCardIds();
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
                    throw new PeerProjectException("修改名片失败");
                }
            }
        }
    }

    @Override
    public List<ReturnCardDTO> findAllPeerByOpenId(String openId) {

        if (StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("用户未登陆");
        }

        List<ReturnCardDTO> returnCardDTOS = userPeerMapper.findAllPeerByOpenId(openId);

        return returnCardDTOS;
    }
}
