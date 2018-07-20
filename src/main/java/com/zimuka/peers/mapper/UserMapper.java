package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.User;

import java.util.List;

public interface UserMapper {

    int save(User user);

    User findOneByOpenId(String openId);

    int update(User user);

    List<User> findUserByParam(User user);
}
