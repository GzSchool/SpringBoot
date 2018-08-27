package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.User;

public interface UserMapper {

    /**
     * 接口描述：保存用户标识
     * @param user
     * @return
     */
    int save(User user);

    /**
     * 接口描述：查询用户标识
     * @param openId
     * @return
     */
    User findOneByOpenId(String openId);

    /**
     * 接口描述：修改用户标识
     * @param user
     * @return
     */
    int update(User user);

}
