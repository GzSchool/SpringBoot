package com.zimuka.peers.service;

import com.zimuka.peers.dao.User;

import java.util.List;

public interface UserService {

    User findOneById(Integer id);

    void saveUser(User user);

    List<User> findUserByParam(User user);
}
