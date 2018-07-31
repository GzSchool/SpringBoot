package com.zimuka.peers.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimuka.peers.configBeans.MiniAppBean;
import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.dto.PageDTO;
import com.zimuka.peers.dto.ReturnCardDTO;
import com.zimuka.peers.enums.PeerCardSaveFlagEnum;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserGroupMapper;
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

    @Autowired
    private MiniAppBean miniAppBean;

    @Resource
    private UserPeerMapper userPeerMapper;

    @Override
    public void saveOrUpdate(UserGroup userGroup) {

        if (StringUtils.isEmpty(userGroup.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        UserGroup checkUserGroup = userGroupMapper.findOneById(userGroup.getOpenId(), userGroup.getGroupId());
        UserGroup saveUserGroup = new UserGroup();
        int rows;
        if (null == checkUserGroup) {

            // TODO 解密groupId 需要传递encryptedData，iv
//            JSONObject jsonObject = WxDecipherUtil.getGroupId(userGroup.getEncryptedData(), checkUser.getSessionKey(), userGroup.getIv());
//
//            System.out.println("~~~~~~~~~~~~~~~解密groupId:" + jsonObject);

            userGroup.setAppId(miniAppBean.getAppId());
            userGroup.setCtTime(new Date());
            BeanUtils.copyProperties(userGroup, saveUserGroup);
            rows = userGroupMapper.save(saveUserGroup);
            if (1 != rows) {
                throw new PeerProjectException("首次分享群名片失败");
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
    public PageDTO findCardsOnGroupByOpenId(String openId, String groupId, Integer pageNum, Integer pageSize) {

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(groupId)) {
            throw new PeerProjectException("参数不完整");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<ReturnCardDTO> returnCardDTOS = userGroupMapper.findCardsOnGroupByOpenId(openId, groupId);

        for (ReturnCardDTO returnCardDTO : returnCardDTOS) {
            UserPeer userPeer = new UserPeer();
            userPeer.setOpenId(openId);
            userPeer.setCardId(returnCardDTO.getId());
            userPeer.setSaveFlag(PeerCardSaveFlagEnum.SAVE_FLAG_TRUE.getKey());
            List<UserPeer> checkUserPeer = userPeerMapper.findUserPeerByParam(userPeer);
            if (0 == checkUserPeer.size()) {
                returnCardDTO.setSaveFlag(PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey());
            } else {
                returnCardDTO.setSaveFlag(PeerCardSaveFlagEnum.SAVE_FLAG_TRUE.getKey());
            }
        }

        PageInfo<ReturnCardDTO> pageInfo = new PageInfo<ReturnCardDTO>(returnCardDTOS);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setResult(pageInfo.getList());
        pageDTO.setPages(pageInfo.getPages());
        pageDTO.setTotal(pageInfo.getTotal());

        return pageDTO;
    }

    @Override
    public List<UserGroup> findUserGroupByParam(UserGroup userGroup) {

        if (StringUtils.isEmpty(userGroup.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        List<UserGroup> userGroupList = userGroupMapper.findUserGroupByParam(userGroup);
        return userGroupList;
    }
}
