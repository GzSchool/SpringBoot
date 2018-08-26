package com.eqxuan.peers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eqxuan.peers.dao.GroupCard;
import com.eqxuan.peers.dao.User;
import com.eqxuan.peers.dao.UserGroup;
import com.eqxuan.peers.dto.GroupNoSaveNumDTO;
import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.enums.PeerCardSaveFlagEnum;
import com.eqxuan.peers.mapper.GroupCardMapper;
import com.eqxuan.peers.mapper.UserGroupMapper;
import com.eqxuan.peers.mapper.UserMapper;
import com.eqxuan.peers.service.UserGroupService;
import com.eqxuan.peers.utils.WxDecipherUtil;
import com.eqxuan.peers.vo.UserGroupVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eqxuan.peers.dto.ReturnGroupDTO;
import com.eqxuan.peers.enums.GroupShareFlagEnum;
import com.eqxuan.peers.exception.PeerProjectException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 微信群操作
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Resource
    private UserGroupMapper userGroupMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private WxDecipherUtil wxDecipherUtil;

    @Resource
    private GroupCardMapper groupCardMapper;

    /**
     * 接口描述：查询群内 除当前用户以外的所有名片 分页（改）
     * @param openId
     * @param groupId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDTO findCardsOnGroupByOpenId(String openId, String groupId, Integer pageNum, Integer pageSize) {

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(groupId)) {
            throw new PeerProjectException("参数不完整");
        }

        if (null == pageNum || "".equals(pageNum) || null == pageSize || "".equals(pageSize)) {
            throw new PeerProjectException("请传入分页数据");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<ReturnCardDTO> returnCardDTOList = groupCardMapper.getCardsOnGroup(groupId, openId);
        List<ReturnCardDTO> saveFalseCard = new ArrayList<ReturnCardDTO>();
        for (ReturnCardDTO returnCardDTO : returnCardDTOList) {
            if (null == returnCardDTO.getSaveFlag() || PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey() == returnCardDTO.getSaveFlag()) {
                returnCardDTO.setSaveFlag(PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey());
                saveFalseCard.add(returnCardDTO);
            }
        }

        PageInfo<ReturnCardDTO> pageInfo = new PageInfo<ReturnCardDTO>(returnCardDTOList);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setResult(pageInfo.getList());
        pageDTO.setPages(pageInfo.getPages());
        pageDTO.setTotal(pageInfo.getTotal());
        pageDTO.setSaveFalseNum(saveFalseCard.size());

        return pageDTO;
    }

    /**
     * 接口描述：搜索群内名片 （改）
     * @param groupId
     * @param openId
     * @param param
     * @return
     */
    @Override
    public List<ReturnCardDTO> findAllGroupCardByParam(String groupId, String openId, String param) {

        if (StringUtils.isEmpty(param)) {
            return null;
        }

        if (StringUtils.isEmpty(groupId) || StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("参数缺失");
        }

        List<ReturnCardDTO> returnCardDTOList = groupCardMapper.findAllGroupCardByParam(groupId, openId, param);

        for (ReturnCardDTO returnCardDTO : returnCardDTOList) {
            if (null == returnCardDTO.getSaveFlag()) {
                returnCardDTO.setSaveFlag(PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey());
            }
        }
        return returnCardDTOList;
    }

    /**
     * 接口描述：去除红点通知
     * @param groupId
     * @param openId
     * @return
     */
    @Override
    public int removeHint(String groupId, String openId) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(groupId);
        userGroup.setOpenId(openId);
        userGroup.setUpTime(new Date());
        userGroup.setHint(0);
        return userGroupMapper.update(userGroup);
    }

    /**
     * 接口描述：用户分享名片到群（新）
     * @param userGroupVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(UserGroupVO userGroupVO) {

        // 校验用户信息
        User checkUser = userMapper.findOneByOpenId(userGroupVO.getMyOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }
        // 解密微信加密信息，获取openGId（群ID）
        JSONObject jsonObject = wxDecipherUtil.getDecipherInfo(userGroupVO.getEncryptedData(), checkUser.getSessionKey(), userGroupVO.getIv());
        if (null == jsonObject) {
            throw new PeerProjectException("获取群ID失败");
        }
        String openGId = jsonObject.getString("openGId");
        System.out.println("##################获取openGId：" + openGId);
        // 判断分享的是否是他人名片
        UserGroup saveUserGroup = new UserGroup();
        int rows;
        saveUserGroup.setOpenId(userGroupVO.getOtherOpenId());
        saveUserGroup.setGroupId(openGId);
        saveUserGroup.setCtTime(new Date());
        if (!userGroupVO.getMyOpenId().equals(userGroupVO.getOtherOpenId())) {
            saveUserGroup.setShare(GroupShareFlagEnum.FLAG_BY_OTHER.getKey());
        } else {
            saveUserGroup.setShare(GroupShareFlagEnum.FLAG_BY_ME.getKey());
        }
        // 用户-群 关系绑定
        UserGroup checkUserGroup = userGroupMapper.findGroup(userGroupVO.getOtherOpenId(), openGId);
        if (null == checkUserGroup) {
            rows = userGroupMapper.save(saveUserGroup);
            if (1 != rows) {
                throw new PeerProjectException("用户绑定群失败");
            }
        } else {
            // 判断是否已经是本人分享名片到群
            if (!checkUserGroup.getShare().equals(GroupShareFlagEnum.FLAG_BY_OTHER.getKey())) {
                saveUserGroup.setShare(GroupShareFlagEnum.FLAG_BY_ME.getKey());
                rows = userGroupMapper.update(saveUserGroup);
                if (1 != rows) {
                    throw new PeerProjectException("用户绑定群失败");
                }
            }
        }
        // 用户-群 绑定后，需要将用户发送的 名片与群 绑定
        // 判断名片是否在群中保存
        GroupCard checkGroupCard = groupCardMapper.findOne(openGId, userGroupVO.getOtherOpenId(), userGroupVO.getCardId());
        if (null == checkGroupCard) {
            GroupCard saveGroupCard = new GroupCard();
            saveGroupCard.setGroupId(openGId);
            saveGroupCard.setOpenId(userGroupVO.getOtherOpenId());
            saveGroupCard.setCardId(Integer.parseInt(userGroupVO.getCardId()));
            saveGroupCard.setCtTime(new Date());
            rows = groupCardMapper.save(saveGroupCard);
            if (1 != rows) {
                throw new PeerProjectException("名片绑定群失败");
            }
            //提示群内其他人有新增用户
            userGroupMapper.hintOthers(openGId, saveUserGroup.getOpenId());
        } else {
            return openGId;
        }
        return openGId;
    }

    /**
     * 接口描述：查询群列表（新）
     * @param openId
     * @param share
     * @return
     */
    @Override
    public List<ReturnGroupDTO> findUserGroupList(String openId, String share) {

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(share)) {
            throw new PeerProjectException("参数缺失");
        }

        List<UserGroup> userGroupList = userGroupMapper.findUserGroupList(openId, share);
        List<ReturnGroupDTO> returnGroupDTOList = new ArrayList<ReturnGroupDTO>(userGroupList.size());
        for (UserGroup userGroup : userGroupList) {
            // 查询每个群前九张不重复的头像
            List<String> photoList = groupCardMapper.getNinePhotoByGroupId(userGroup.getGroupId());
            // 查询当前用户每个群的名片保存数量
            List<GroupNoSaveNumDTO> saveOrNotNum = groupCardMapper.countSaveOrNot(userGroup.getGroupId(), userGroup.getOpenId());
            int saveFalseNum = 0;
            int saveTrueNum = 0;
            for (GroupNoSaveNumDTO noSaveNum : saveOrNotNum) {
                if (null == noSaveNum.getSaveFlag() || noSaveNum.getSaveFlag().intValue() == PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey()) {
                    saveFalseNum += noSaveNum.getNum();
                } else {
                    saveTrueNum += noSaveNum.getNum();
                }
            }
            ReturnGroupDTO returnGroupDTO = new ReturnGroupDTO();
            returnGroupDTO.setGroupId(userGroup.getGroupId());
            returnGroupDTO.setOpenId(userGroup.getOpenId());
            returnGroupDTO.setCtTime(userGroup.getCtTime());
            returnGroupDTO.setSaveTrue(saveTrueNum);
            returnGroupDTO.setSaveFalse(saveFalseNum);
            returnGroupDTO.setBeforeNineImg(photoList);
            returnGroupDTO.setHint(userGroup.getHint());

            returnGroupDTOList.add(returnGroupDTO);
        }
        return returnGroupDTOList;
    }

    /**
     * 接口描述：群内个人名片（新）
     * @param groupId
     * @param openId
     * @return
     */
    @Override
    public List<ReturnCardDTO> getOwnerCardsOnGroup(String groupId, String openId) {

        if (StringUtils.isEmpty(groupId) || StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("参数缺失");
        }
        List<ReturnCardDTO> returnCardDTOList = groupCardMapper.getOwnerCardsOnGroup(groupId, openId);

        return returnCardDTOList;
    }
}
