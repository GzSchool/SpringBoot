package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.User;

public interface UserMapper {

    User findOneById (Integer id);

    int saveUser(User user);

    User findOneByOpenId(String openId);
}