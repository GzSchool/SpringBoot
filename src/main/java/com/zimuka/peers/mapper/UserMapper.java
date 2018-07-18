package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.User;

public interface UserMapper {

    /**
     * 查询用户
     * @param id
     * @return
     */
    User findOneById (Integer id);
}