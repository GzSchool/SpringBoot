package com.eqxuan.peers.service.impl;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dao.UserPeer;
import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.enums.PeerCardSaveFlagEnum;
import com.eqxuan.peers.enums.PeerShareFlagEnum;
import com.eqxuan.peers.mapper.UserCardMapper;
import com.eqxuan.peers.mapper.UserPeerMapper;
import com.eqxuan.peers.service.UserPeerService;
import com.eqxuan.peers.service.WxTemplateService;
import com.eqxuan.peers.service.cache.CacheManager;
import com.eqxuan.peers.utils.DateUtil;
import com.eqxuan.peers.vo.CreatePeersVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eqxuan.peers.exception.PeerProjectException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserPeerServiceImpl implements UserPeerService {

    private static final Logger logger = LoggerFactory.getLogger(UserPeerServiceImpl.class);

    @Resource
    private UserPeerMapper userPeerMapper;

    @Resource
    private CacheManager cacheManager;

    @Resource
    private UserCardMapper userCardMapper;

    @Resource
    private WxTemplateService wxTemplateService;
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

        logger.info("【卡片集合】：{}", createPeersVO.getCardIds());


        if (StringUtils.isEmpty(createPeersVO.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        List<Integer> cardIdList = createPeersVO.getCardIds();

        logger.info("【获取卡片集合】：{}", cardIdList);


        int rows;
        UserPeer saveUserPeer = new UserPeer();
        for (int cardId : cardIdList) {
            UserCard checkUserCard = userCardMapper.findById(cardId);
            if (null == checkUserCard) {
                throw new PeerProjectException("名片不存在");
            }

            UserPeer checkUserPeer = userPeerMapper.findOne(createPeersVO.getOpenId(), cardId);
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

                JSONObject jsonObject = wxTemplateService.saveCardSuccess(checkUserCard.getOpenId(), createPeersVO.getSaveName() , createPeersVO.getFromId(), DateUtil.dateToString(saveUserPeer.getCtTime()));
                logger.info("【模板消息推送】：{}", jsonObject);

            } else {
                saveUserPeer.setUpTime(new Date());
                saveUserPeer.setId(checkUserPeer.getId());
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

    @Override
    public boolean checkSave(String openId, String cardId) {

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(cardId)) {
            throw new PeerProjectException("参数缺失");
        }

        UserPeer checkPeer = userPeerMapper.findOne(openId, Integer.parseInt(cardId));
        if (null == checkPeer || checkPeer.getSaveFlag() == PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey()) {
            return false;
        } else {
            return true;
        }
    }
}
