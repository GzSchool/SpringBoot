package com.zimuka.peers.service;

import com.zimuka.peers.dao.UserGroup;

import java.util.List;

public interface UserGroupService {

    void saveOrUpdate(UserGroup userGroup);

    List<UserGroup> findAllByUserGroup(UserGroup userGroup);
}
