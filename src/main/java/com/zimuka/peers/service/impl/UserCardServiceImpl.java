package com.zimuka.peers.service.impl;

import com.zimuka.peers.configBeans.MiniAppBean;
import com.zimuka.peers.dao.User;
import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dto.UserCardDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserCardMapper;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.service.UserCardService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserCardServiceImpl implements UserCardService {

    @Resource
    private UserCardMapper userCardMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MiniAppBean miniAppBean;

    @Override
    public void saveOrUpdate(UserCard userCard) {

        if (StringUtils.isEmpty(userCard.getWechatNum()) || StringUtils.isEmpty(userCard.getCompany()) || StringUtils.isEmpty(userCard.getIndustry()) || StringUtils.isEmpty(userCard.getCity())) {
            throw new PeerProjectException("必填字段不可为空");
        }

        if (null == userCard.getOpenId() || "".equals(userCard.getOpenId())) {
            throw new PeerProjectException("用户未注册");
        }

        User user = userMapper.findOneByOpenId(userCard.getOpenId());
        if (null == user) {
            throw new PeerProjectException("用户未注册");
        }

        UserCardDTO checkUserCard = userCardMapper.findOneById(user.getId());
        UserCard saveUserCard = new UserCard();
        int rows;
        if (null == checkUserCard) {
            userCard.setUserId(user.getId());
            userCard.setAppId(miniAppBean.getAppId());
            BeanUtils.copyProperties(userCard, saveUserCard);
            rows = userCardMapper.save(saveUserCard);
            if (1 != rows) {
                throw new PeerProjectException("保存名片失败");
            }
        } else {
            userCard.setUserId(user.getId());
            userCard.setAppId(miniAppBean.getAppId());
            BeanUtils.copyProperties(userCard, saveUserCard);
            rows = userCardMapper.updateCardByUser(saveUserCard);
            if (1 != rows) {
                throw new PeerProjectException("修改名片失败");
            }
        }
    }

    @Override
    public UserCardDTO findOneByUser(String openId) {

        if (StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("openId不能为空");
        }

        User user = userMapper.findOneByOpenId(openId);
        if (null == user) {
            throw new PeerProjectException("用户未注册");
        }

        UserCardDTO userCardDTO = userCardMapper.findOneById(user.getId());
        if (null == userCardDTO) {
            throw new PeerProjectException("添加我的信息");
        }
        return userCardDTO;
    }

    @Override
    public List<UserCard> findUserCardByParam(UserCard userCard) {

        User checkUser = userMapper.findOneByOpenId(userCard.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }
        userCard.setUserId(checkUser.getId());

        List<UserCard> userCardList = userCardMapper.findCardByParam(userCard);

        return userCardList;
    }

}
