package com.zimuka.peers.service.impl;

import com.zimuka.peers.configBeans.MiniAppBean;
import com.zimuka.peers.dao.User;
import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.dto.CardsOnGroupDTO;
import com.zimuka.peers.enums.PeerShareFlagEnum;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserCardMapper;
import com.zimuka.peers.mapper.UserGroupMapper;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.mapper.UserPeerMapper;
import com.zimuka.peers.service.UserGroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Resource
    private UserGroupMapper userGroupMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MiniAppBean miniAppBean;

    @Resource
    private UserCardMapper userCardMapper;

    @Resource
    private UserPeerMapper userPeerMapper;

    @Override
    public void saveOrUpdate(UserGroup userGroup) {

        if (StringUtils.isEmpty(userGroup.getGroupId())) {
            throw new PeerProjectException("请选择要分享的群");
        }

        if (StringUtils.isEmpty(userGroup.getOpenId())) {
            throw new PeerProjectException("缺少用户标识");
        }

        User checkUser = userMapper.findOneByOpenId(userGroup.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        UserGroup checkUserGroup = userGroupMapper.findOneById(userGroup.getOpenId(), userGroup.getGroupId());
        UserGroup saveUserGroup = new UserGroup();
        int rows;
        if (null == checkUserGroup) {
            userGroup.setAppId(miniAppBean.getAppId());
            userGroup.setCtTime(new Date());
            BeanUtils.copyProperties(userGroup, saveUserGroup);
            rows = userGroupMapper.save(saveUserGroup);
            if (1 != rows) {
                throw new PeerProjectException("首次分享群名片失败");
            }

            //分享到群，群成员都可以看到，可以让每个群成员都保存对应的名片映射
            List<UserGroup> userGroupList = userGroupMapper.findUsersByGroupId(userGroup.getGroupId(), userGroup.getOpenId());
            //本人分享到群中的名片
            UserCard userCard = userCardMapper.findOneByOpenId(userGroup.getOpenId());
            UserPeer userPeer = new UserPeer();
            for (UserGroup userGroup1 : userGroupList) {
                userPeer.setCardId(userCard.getId());
                userPeer.setOpenId(userGroup1.getOpenId());
                userPeer.setGroupId(userGroup1.getGroupId());
                userPeer.setShareFlag(PeerShareFlagEnum.FLAG_BY_GROUP.getKey());
                userPeer.setCtTime(new Date());
                rows = userPeerMapper.save(userPeer);
                if (1 != rows) {
                    throw new PeerProjectException("分享群名片失败");
                }
            }
        } else {
            userGroup.setUpTime(new Date());
            BeanUtils.copyProperties(userGroup, saveUserGroup);
            rows = userGroupMapper.update(saveUserGroup);
            if (1 != rows) {
                throw new PeerProjectException("分享群名片失败");
            }

        }
    }

    @Override
    public List<CardsOnGroupDTO> findCardsOnGroupByOpenId(String openId, String groupId) {

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(groupId)) {
            throw new PeerProjectException("参数不完整");
        }

        List<CardsOnGroupDTO> cardsOnGroupDTOS = userGroupMapper.findCardsOnGroupByOpenId(openId, groupId);

        return cardsOnGroupDTOS;
    }
}
