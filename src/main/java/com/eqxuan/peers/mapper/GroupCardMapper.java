package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dto.GroupNoSaveNumDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Mature
 * @Description: 群名片Mapper
 * @date 2018/8/25 18:07
 */
public interface GroupCardMapper {

	/**
	 * 接口描述：查询群内保存状态的数目
	 * @param groupId 当前群ID
	 * @param openId 当前用户标识
	 * @return
	 */
	List<GroupNoSaveNumDTO> countSaveOrNot(@Param("groupId") String groupId,
										  @Param("openId") String openId);

	/**
	 * 接口描述：返回群内前九个人的头像信息
	 * @param groupId
	 * @return
	 */
	List<String> getNinePhotoByGroupId(String groupId);

	/**
	 * 接口描述：群内个人名片
	 * @param groupId
	 * @param openId 当前用户标识
	 * @return
	 */
	List<ReturnCardDTO> getOwnerCardsOnGroup(String groupId, String openId);

	/**
	 * 接口描述：群内名片列表
	 * @param groupId
	 * @param openId 当前用户标识
	 * @return
	 */
	List<ReturnCardDTO> getCardsOnGroup(String groupId, String openId);

	/**
	 * 接口描述：搜索群内名片
	 * @param groupId 当前群ID
	 * @param openId 当前用户标识
	 * @param param 搜索参数
	 * @return
	 */
	List<ReturnCardDTO> findAllGroupCardByParam(@Param("groupId") String groupId,
												@Param("openId") String openId,
												@Param("param") String param);
}
