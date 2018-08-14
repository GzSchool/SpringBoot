package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.UserFromId;

import java.util.List;

/**
 * @author: zheng guangjing.
 * @date: 2018/8/14 16:11
 * @description: description
 */
public interface UserFromIdMapper {

    /**
     * 收集用户FromId
     * @param userFromId
     * @return
     */
    int save(UserFromId userFromId);

    /**
     * 消耗用户FromId
     * @param userFromId
     * @return
     */
    int update(UserFromId userFromId);

    /**
     * 查询用户的FromId
     * @param userFromId
     * @return
     */
    List<UserFromId> listUserFromIds(UserFromId userFromId);

    /**
     * 清理过期和已使用得FromId
     * @param userFromId
     * @return
     */
    int delete(UserFromId userFromId);

    /**
     * 查询当前用户最新的一条可用表单Id
     * @param userFromId
     * @return
     */
    UserFromId getNowFromIdByOpenId(UserFromId userFromId);
}
