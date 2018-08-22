package com.eqxuan.peers.service;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.ReturnCardDTO;

import java.util.List;

public interface UserCardService {

    /**
     * 接口描述：保存或修改名片信息
     * @param userCard
     */
    void saveOrUpdate(UserCard userCard);

    /**
     * 接口描述：根据用户标识查询名片信息
     * @param openId
     * @return
     */
    UserCard findOneByOpenId(String openId);

    /**
     * 接口描述：根据入参查询名片信息
     * @param userCard
     * @return
     */
    List<UserCard> findCardByParam(UserCard userCard);

    /**
     * 接口描述：在当前用户的名片夹进行搜索操作
     * @param param
     * @param openId
     * @return
     */
    List<ReturnCardDTO> findAllByPeerAndParam(String param, String openId);

    /**
     * 接口描述：添加用户名片
     * @param userCard
     * @return
     */
    int save(UserCard userCard);

    /**
     * 接口描述：修改用户接口
     * @param userCard
     */
    void update(UserCard userCard);

    /**
     * 查询个人名片列表
     * @param openId
     * @return
     */
    List<ReturnCardDTO> findCardListByOpenId(String openId);
}
