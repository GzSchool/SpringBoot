package com.zimuka.peers.service.impl;

import com.zimuka.peers.dao.User;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findOneById(Integer id) {
        User user = userMapper.findOneById(id);
        return user;
    }
}
