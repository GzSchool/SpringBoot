package com.zimuka.peers.service.impl;

import com.zimuka.peers.dao.User;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findOneById(Integer id) {
        User user = userMapper.findOneById(id);
        return user;
    }

    @Override
    public void saveUser(User user) {
        int rows = userMapper.saveUser(user);
        if (1 != rows) {
            throw new PeerProjectException("添加用户失败");
        }
    }

    @Override
    public List<User> findUserByParam(User user) {

        List<User> userList = userMapper.findUserByParam(user);

        return userList;
    }
}
