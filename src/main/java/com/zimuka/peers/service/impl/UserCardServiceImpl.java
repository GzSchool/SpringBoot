package com.zimuka.peers.service.impl;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserCardMapper;
import com.zimuka.peers.service.UserCardService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserCardServiceImpl implements UserCardService {

    @Resource
    private UserCardMapper userCardMapper;

    @Override
    public void saveOrUpdate(UserCard userCard) {

        if (StringUtils.isEmpty(userCard.getWechatNum()) || StringUtils.isEmpty(userCard.getCompany()) || StringUtils.isEmpty(userCard.getIndustry()) || StringUtils.isEmpty(userCard.getCity())) {
            throw new PeerProjectException("必填字段不可为空");
        }

        if (null == userCard.getUserId() || "".equals(userCard.getUserId())) {
            throw new PeerProjectException("用户未注册");
        }

        UserCard checkUserCard = userCardMapper.findOneById(userCard.getUserId());
        UserCard saveUserCard = new UserCard();
        int rows;
        if (null == checkUserCard) {
            BeanUtils.copyProperties(userCard, saveUserCard);
            rows = userCardMapper.save(saveUserCard);
            if (1 != rows) {
                throw new PeerProjectException("保存名片失败");
            }
        } else {
            BeanUtils.copyProperties(userCard, saveUserCard);
            rows = userCardMapper.updateCardByUser(saveUserCard);
            if (1 != rows) {
                throw new PeerProjectException("修改名片失败");
            }
        }
    }

    @Override
    public UserCard findOneByUser(Integer userId) {

        UserCard userCard = userCardMapper.findOneById(userId);
        if (null == userCard) {
            throw new PeerProjectException("添加我的信息");
        }
        return userCard;
    }

}
