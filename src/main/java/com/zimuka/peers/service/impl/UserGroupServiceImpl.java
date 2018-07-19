package com.zimuka.peers.service.impl;

import com.zimuka.peers.dao.User;
import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserGroupMapper;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.service.UserGroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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

    @Override
    public void saveOrUpdate(UserGroup userGroup) {

        if (StringUtils.isEmpty(userGroup.getGroupId())) {
            throw new PeerProjectException("群组ID不可为空");
        }

        if (null == userGroup.getOpenId() || "".equals(userGroup.getOpenId())) {
            throw new PeerProjectException("参数异常：openId不可为空");
        }

        User checkUser = userMapper.findOneByOpenId(userGroup.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }
        int rows;
        UserGroup checkUserGroup = userGroupMapper.findOneById(checkUser.getId(), userGroup.getGroupId());
        if (null == checkUserGroup) {
            userGroup.setCreateTime(new Date());
            rows = userGroupMapper.save(userGroup);
            if (1 != rows) {
                throw new PeerProjectException("添加群信息失败");
            }
        } else {
            userGroup.setUpTime(new Date());
            rows = userGroupMapper.update(userGroup);
            if (1 != rows) {
                throw new PeerProjectException("修改群信息失败");
            }
        }
    }

    @Override
    public List<UserGroup> findAllByUserGroup(UserGroup userGroup) {

        if (null == userGroup.getOpenId() || "".equals(userGroup.getOpenId())) {
            throw new PeerProjectException("参数异常：openId不可为空");
        }

        User checkUser = userMapper.findOneByOpenId(userGroup.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }
        userGroup.setUserId(checkUser.getId());

        List<UserGroup> userGroupList = userGroupMapper.findAllByUserGroup(userGroup);

        return userGroupList;
    }
}
