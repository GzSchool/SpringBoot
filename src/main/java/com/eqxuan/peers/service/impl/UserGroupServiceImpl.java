package com.eqxuan.peers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eqxuan.peers.dao.User;
import com.eqxuan.peers.dao.UserGroup;
import com.eqxuan.peers.dto.GroupNoSaveNumDTO;
import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.enums.PeerCardSaveFlagEnum;
import com.eqxuan.peers.mapper.UserGroupMapper;
import com.eqxuan.peers.mapper.UserMapper;
import com.eqxuan.peers.service.UserGroupService;
import com.eqxuan.peers.utils.WxDecipherUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eqxuan.peers.config.MiniAppBean;
import com.eqxuan.peers.dto.ReturnGroupDTO;
import com.eqxuan.peers.enums.GroupShareFlagEnum;
import com.eqxuan.peers.exception.PeerProjectException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private MiniAppBean miniAppBean;

    @Resource
    private UserMapper userMapper;

    @Resource
    private WxDecipherUtil wxDecipherUtil;

    @Override
    public String saveOrUpdate(UserGroup userGroup) {

        if (StringUtils.isEmpty(userGroup.getOpenId()) || StringUtils.isEmpty(userGroup.getOtherOpenId()) || StringUtils.isEmpty(userGroup.getEncryptedData()) || StringUtils.isEmpty(userGroup.getIv())) {
            throw new PeerProjectException("参数缺失");
        }

        User checkUser = userMapper.findOneByOpenId(userGroup.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        // 解密groupId 需要传递encryptedData，iv
        JSONObject jsonObject = wxDecipherUtil.getDecipherInfo(userGroup.getEncryptedData(), checkUser.getSessionKey(), userGroup.getIv());

        if (null == jsonObject) {
            throw new PeerProjectException("获取群ID失败");
        }

        String openGId = jsonObject.getString("openGId");
        System.out.println("##################获取openGId：" + openGId);

        UserGroup saveUserGroup = new UserGroup();
        //判断是否分享的是他人的名片
        if (!userGroup.getOtherOpenId().equals(userGroup.getOpenId())) {
            BeanUtils.copyProperties(userGroup, saveUserGroup);
            saveUserGroup.setOpenId(userGroup.getOtherOpenId());
            saveUserGroup.setPrepare(GroupShareFlagEnum.FLAG_BY_OTHER.getKey());
        } else {
            BeanUtils.copyProperties(userGroup, saveUserGroup);
            saveUserGroup.setOpenId(userGroup.getOpenId());
            saveUserGroup.setPrepare(GroupShareFlagEnum.FLAG_BY_ME.getKey());
        }

        UserGroup checkUserGroup = userGroupMapper.findOneById(userGroup.getOtherOpenId(), openGId, userGroup.getCardId());

        int rows;
        if (null == checkUserGroup) {
            saveUserGroup.setAppId(miniAppBean.getAppId());
            saveUserGroup.setCtTime(new Date());
            saveUserGroup.setGroupId(openGId);
            rows = userGroupMapper.save(saveUserGroup);
            if (1 != rows) {
                throw new PeerProjectException("首次分享群名片失败");
            }

            //提示群内其他人有新增用户
            rows = userGroupMapper.hintOthers(openGId, saveUserGroup.getOpenId());

            return openGId;

        } else {
            saveUserGroup.setUpTime(new Date());
            saveUserGroup.setGroupId(openGId);
            if (!checkUserGroup.getPrepare().equals(GroupShareFlagEnum.FLAG_BY_OTHER.getKey())) {
                saveUserGroup.setPrepare(checkUserGroup.getPrepare());
            }
            rows = userGroupMapper.update(saveUserGroup);
            if (1 != rows) {
                throw new PeerProjectException("分享群名片失败");
            }
            return openGId;
        }
    }

    @Override
    public PageDTO findCardsOnGroupByOpenId(String openId, String groupId, Integer pageNum, Integer pageSize) {

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(groupId)) {
            throw new PeerProjectException("参数不完整");
        }

        if (null == pageNum || "".equals(pageNum) || null == pageSize || "".equals(pageSize)) {
            throw new PeerProjectException("请传入分页数据");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<ReturnCardDTO> returnCardDTOS = userGroupMapper.findCardsOnGroupByOpenId(openId, groupId);
        List<ReturnCardDTO> saveFalseCard = new ArrayList<ReturnCardDTO>();
        for (ReturnCardDTO returnCard : returnCardDTOS) {
            if (null == returnCard.getSaveFlag() || PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey() == returnCard.getSaveFlag()) {
                returnCard.setSaveFlag(PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey());
                saveFalseCard.add(returnCard);
            }
        }

        PageInfo<ReturnCardDTO> pageInfo = new PageInfo<ReturnCardDTO>(returnCardDTOS);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setResult(pageInfo.getList());
        pageDTO.setPages(pageInfo.getPages());
        pageDTO.setTotal(pageInfo.getTotal());
        pageDTO.setSaveFalseNum(saveFalseCard.size());

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

            List<GroupNoSaveNumDTO> noSaveNumList = userGroupMapper.countByNoSave(group.getGroupId(), userGroup.getOpenId());

            int saveFalseNum = 0;
            int saveTrueNum = 0;
            for (GroupNoSaveNumDTO noSaveNum : noSaveNumList) {
                if (null == noSaveNum.getSaveFlag() || noSaveNum.getSaveFlag().intValue() == PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey()) {
                    saveFalseNum += noSaveNum.getNum();
                } else {
                    saveTrueNum += noSaveNum.getNum();
                }
            }

            List<String> beforeNineImg = userGroupMapper.getNineBeforeByGroupId(group.getGroupId());

            ReturnGroupDTO returnGroupDTO = new ReturnGroupDTO();

            returnGroupDTO.setGroupId(group.getGroupId());
            returnGroupDTO.setOpenId(userGroup.getOpenId());
            returnGroupDTO.setCtTime(group.getCtTime());
            returnGroupDTO.setUpTime(group.getUpTime());
            returnGroupDTO.setSaveFalse(saveFalseNum);
            returnGroupDTO.setSaveTrue(saveTrueNum);
            returnGroupDTO.setBeforeNineImg(beforeNineImg);
            returnGroupDTO.setHint(group.getHint());
            returnGroupDTOS.add(returnGroupDTO);
        }
        return returnGroupDTOS;
    }

    @Override
    public List<ReturnCardDTO> findAllGroupCardByParam(String groupId, String openId, String param) {

        if (StringUtils.isEmpty(param)) {
            return null;
        }

        if (StringUtils.isEmpty(groupId) || StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("参数缺失");
        }

        List<ReturnCardDTO> returnCardDTOS = userGroupMapper.findAllGroupCardByParam(groupId, openId, param);

        for (ReturnCardDTO returnCardDTO : returnCardDTOS) {
            if (null == returnCardDTO.getSaveFlag()) {
                returnCardDTO.setSaveFlag(PeerCardSaveFlagEnum.SAVE_FLAG_FALSE.getKey());
            }
        }
        return returnCardDTOS;
    }

    @Override
    public int removeHint(String groupId, String openId) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(groupId);
        userGroup.setOpenId(openId);
        userGroup.setUpTime(new Date());
        userGroup.setHint(0);
        return userGroupMapper.update(userGroup);
    }

}
