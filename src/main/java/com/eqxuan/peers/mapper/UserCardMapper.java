package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.ReturnCardDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserCardMapper {

    /**
     * 接口描述：保存名片信息
     * @param userCard
     * @return
     */
    int save(UserCard userCard);

    /**
     * 接口描述：修改名片信息
     * @param userCard
     * @return
     */
    int update(UserCard userCard);

    /**
     * 接口描述：根据用户标识查询名片信息
     * @param openId 用户标识
     * @return
     */
    List<UserCard> findOneByOpenId(String openId);

    /**
     * 接口描述：根据入参查询名片信息
     * @param userCard
     * @return
     */
    List<UserCard> findCardByParam(UserCard userCard);

    /**
     * 接口描述：查询名片信息
     * @param id 名片ID
     * @return
     */
	UserCard findById(int id);

    /**
     * 接口描述：在当前用户的名片夹进行搜索操作
     * @param param 搜索参数
     * @param openId 当前用户标识
     * @return
     */
	List<ReturnCardDTO> findAllByPeerAndParam(@Param("param") String param,
                                              @Param("openId") String openId);

	/**
	 * @Description: 查询个人名片列表
	 *
	 * @auther: Mature
	 */
    List<ReturnCardDTO> findCardListByOpenId(@Param("openId") String openId);
}
