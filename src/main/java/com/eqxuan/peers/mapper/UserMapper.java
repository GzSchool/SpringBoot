package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.User;

import java.util.List;

public interface UserMapper {

    int save(User user);

    User findOneByOpenId(String openId);

    int update(User user);

}
