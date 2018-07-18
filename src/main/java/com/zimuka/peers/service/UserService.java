package com.zimuka.peers.service;

import com.zimuka.peers.dao.User;

public interface UserService {
    User findOneById(Integer id);
}
