package com.zimuka.peers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimuka.peers.configBeans.MiniAppBean;
import com.zimuka.peers.dao.User;
import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.dto.PageDTO;
import com.zimuka.peers.dto.ReturnCardDTO;
import com.zimuka.peers.dto.ReturnGroupDTO;
import com.zimuka.peers.enums.GroupShareFlagEnum;
import com.zimuka.peers.enums.PeerCardSaveFlagEnum;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserGroupMapper;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.mapper.UserPeerMapper;
import com.zimuka.peers.service.UserGroupService;
import com.zimuka.peers.utils.WxDecipherUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private UserMapper userMapper;

    @Override
    public void saveOrUpdate(UserGroup userGroup) {

        if (StringUtils.isEmpty(userGroup.getOpenId()) || StringUtils.isEmpty(userGroup.getOtherOpenId())) {
            throw new PeerProjectException("参数缺失");
        }

        User checkUser = userMapper.findOneByOpenId(userGroup.getOpenId());

        // 解密groupId 需要传递encryptedData，iv
        JSONObject jsonObject = WxDecipherUtil.getGroupId(userGroup.getEncryptedData(), checkUser.getSessionKey(), userGroup.getIv());
        String openGId = jsonObject.getString("openGId");
        System.out.println("##################获取openGId：" + openGId);

        UserGroup checkUserGroup;
        UserGroup saveUserGroup = new UserGroup();
        //判断是否分享的是他人的名片
        if (!userGroup.getOtherOpenId().equals(userGroup.getOpenId())) {
            BeanUtils.copyProperties(userGroup, saveUserGroup);
            saveUserGroup.setOpenId(userGroup.getOtherOpenId());
            saveUserGroup.setPrepare(GroupShareFlagEnum.FLAG_BY_OTHER.getKey());
            checkUserGroup = userGroupMapper.findOneById(userGroup.getOtherOpenId(), openGId);
        } else {
            BeanUtils.copyProperties(userGroup, saveUserGroup);
            saveUserGroup.setOpenId(userGroup.getOpenId());
            saveUserGroup.setPrepare(GroupShareFlagEnum.FLAG_BY_ME.getKey());
            checkUserGroup = userGroupMapper.findOneById(userGroup.getOpenId(), openGId);
        }

        int rows;
        if (null == checkUserGroup) {
            saveUserGroup.setAppId(miniAppBean.getAppId());
            saveUserGroup.setCtTime(new Date());
            saveUserGroup.setGroupId(openGId);
            rows = userGroupMapper.save(saveUserGroup);
            if (1 != rows) {
                throw new PeerProjectException("首次分享群名片失败");
            }
        } else {
            saveUserGroup.setUpTime(new Date());
            if (!checkUserGroup.getPrepare().equals(GroupShareFlagEnum.FLAG_BY_OTHER.getKey())) {
                saveUserGroup.setPrepare(checkUserGroup.getPrepare());
            }
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
    public List<ReturnGroupDTO> findUserGroupByParam(UserGroup userGroup) {

        if (StringUtils.isEmpty(userGroup.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        if (StringUtils.isEmpty(userGroup.getPrepare())) {
            throw new PeerProjectException("参数Prepare不可为空");
        }

        //查询当前用户所有的群
        List<UserGroup> userGroupList = userGroupMapper.findUserGroupByParam(userGroup);
        List<ReturnGroupDTO> returnGroupDTOS = new ArrayList<ReturnGroupDTO>(userGroupList.size());

        for (UserGroup group : userGroupList) {

            ReturnGroupDTO returnGroupDTO = new ReturnGroupDTO();

            //根据群ID和当前用户的openId,查询出当前群 的除了但前用户的 名片集合
            List<ReturnCardDTO> returnCardDTOS = userGroupMapper.findCardsOnGroupByOpenId(userGroup.getOpenId(), group.getGroupId());

            List saveTrue = new ArrayList();
            List saveFalse = new ArrayList();

            //在根据名片ID与当前用户的OPENID，查询是否保存
            for (ReturnCardDTO returnCardDTO : returnCardDTOS) {
                UserPeer checkUserPeer = userPeerMapper.findOneById(userGroup.getOpenId(), returnCardDTO.getId());
                if (null == checkUserPeer) {
                    saveFalse.add(checkUserPeer);
                }else {
                    saveTrue.add(checkUserPeer);
                }
            }

            returnGroupDTO.setGroupId(group.getGroupId());
            returnGroupDTO.setOpenId(userGroup.getOpenId());
            returnGroupDTO.setCtTime(group.getCtTime());
            returnGroupDTO.setUpTime(group.getUpTime());
            returnGroupDTO.setSaveTrue(saveTrue.size());
            returnGroupDTO.setSaveFalse(saveFalse.size());

            returnGroupDTOS.add(returnGroupDTO);
        }
        return returnGroupDTOS;
    }
}
