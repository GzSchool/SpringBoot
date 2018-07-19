package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.User;

import java.util.List;

public interface UserMapper {

    User findOneById (Integer id);

    int saveUser(User user);

    User findOneByOpenId(String openId);

    int updateUser(User user);

    List<User> findUserByParam(User user);
}