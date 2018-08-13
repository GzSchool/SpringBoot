package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.UserGroup;
import com.eqxuan.peers.dto.GroupNoSaveNumDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMapper {

    /**
     * 接口描述：保存群信息
     * @param userGroup
     * @return
     */
    int save(UserGroup userGroup);

    /**
     * 接口描述：修改群信息
     * @param userGroup
     * @return
     */
    int update(UserGroup userGroup);

    /**
     * 接口描述：查询当前群信息
     * @param openId 当前用户标识
     * @param groupId 当前群ID
     * @return
     */
    UserGroup findOneById(@Param("openId") String openId,
                          @Param("groupId") String groupId);

    /**
     * 接口描述：查询群列表
     * @param userGroup
     * @return
     */
    List<UserGroup> findUserGroupByParam(UserGroup userGroup);

    /**
     * 接口描述：查询群内 除当前用户以外的所有名片
     * @param openId 当前用户标识
     * @param groupId 当前群ID
     * @return
     */
    List<ReturnCardDTO> findCardsOnGroupByOpenId(@Param("openId") String openId,

                                                 @Param("groupId") String groupId);

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

    /**
     * 接口描述：查询群内未保存数目
     * @param groupId 当前群ID
     * @param openId 当前用户标识
     * @return
     */
    List<GroupNoSaveNumDTO> countByNoSave(@Param("groupId") String groupId,
                                          @Param("openId") String openId);

    /**
     * 接口描述：返回群内前九个人的图片信息
     * @param groupId
     * @return
     */
    List<String> getNineBeforeByGroupId(String groupId);

    /**
     * 接口描述：添加红点消息提示
     * @param groupId
     * @param openId
     * @return
     */
    int hintOthers(@Param("groupId") String groupId,
                   @Param("openId") String openId);
}
