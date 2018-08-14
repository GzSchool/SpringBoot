package com.eqxuan.peers.service.impl;

import com.eqxuan.peers.dao.User;
import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dao.UserFromId;
import com.eqxuan.peers.dao.UserPeer;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.enums.PeerCardSaveFlagEnum;
import com.eqxuan.peers.enums.PeerShareFlagEnum;
import com.eqxuan.peers.mapper.UserCardMapper;
import com.eqxuan.peers.mapper.UserFromIdMapper;
import com.eqxuan.peers.mapper.UserMapper;
import com.eqxuan.peers.mapper.UserPeerMapper;
import com.eqxuan.peers.service.UserPeerService;
import com.eqxuan.peers.service.WxTemplateService;
import com.eqxuan.peers.vo.CreatePeersVO;
import com.eqxuan.peers.exception.PeerProjectException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserPeerServiceImpl implements UserPeerService {

    private static final Logger logger = LoggerFactory.getLogger(UserPeerServiceImpl.class);

    @Resource
    private UserPeerMapper userPeerMapper;

    @Resource
    private UserCardMapper userCardMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private WxTemplateService wxTemplateService;

    @Resource
    private UserFromIdMapper userFromIdMapper;

    @Override
    public void saveOrUpdate(CreatePeersVO createPeersVO) {

        if (StringUtils.isEmpty(createPeersVO.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }
        User checkUser = userMapper.findOneByOpenId(createPeersVO.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        List<Integer> cardIdList = createPeersVO.getCardIds();
        logger.info("【获取卡片集合】：{}", cardIdList);
        if (cardIdList.size() == 0) {
            throw new PeerProjectException("请选择你要保存的同行名片");
        }

        int rows;
        UserPeer saveUserPeer = new UserPeer();
        for (int cardId : cardIdList) {
            //校验名片是否存在
            UserCard checkUserCard = userCardMapper.findById(cardId);
            if (null == checkUserCard) {
                throw new PeerProjectException("该名片不存在");
            }
            //校验当前用户是否保存过当前名片
            UserPeer checkUserPeer = userPeerMapper.findOne(createPeersVO.getOpenId(), cardId);
            saveUserPeer.setCardId(cardId);
            if ("0".equals(createPeersVO.getGroupId())) {
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

                Date nowDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(nowDate);
                cal.add(Calendar.DAY_OF_MONTH, -7);
                Date checkDate = cal.getTime();

                UserFromId userFromId = new UserFromId();
                userFromId.setOpenId(checkUserCard.getOpenId());
                userFromId.setCtTime(checkDate);

                UserFromId checkFromId = userFromIdMapper.getNowFromIdByOpenId(userFromId);
                if (null != checkFromId) {
                    // TODO 消息推送
                    JSONObject jsonObject = wxTemplateService.saveCardSuccess(checkFromId.getOpenId(), createPeersVO.getSaveName() , checkFromId.getFromId());
                    logger.info("【模板消息推送】：{}", jsonObject);

                    checkFromId.setStatus(2);
                    rows = userFromIdMapper.update(checkFromId);
                    if (1 != rows) {
                        throw new PeerProjectException("消息模板推送失败");
                    }
                }
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

    @Override
    public boolean addRemark(String openId, String cardId, String remark) {
        UserPeer checkPeer = userPeerMapper.findOne(openId, Integer.parseInt(cardId));
        boolean result = false;
        if(checkPeer != null){
            checkPeer.setUpTime(new Date());
            checkPeer.setRemark(remark);
            int rows = userPeerMapper.update(checkPeer);
            if (1 != rows) {
                throw new PeerProjectException("添加备注失败");
            }
            result = true;
        }
        return result;
    }

    @Override
    public ReturnCardDTO getPeerInfo(String openId, String cardId) {
        if (StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("用户未登陆");
        }
        return userPeerMapper.getPeerInfo(openId, Integer.parseInt(cardId));
    }
}
