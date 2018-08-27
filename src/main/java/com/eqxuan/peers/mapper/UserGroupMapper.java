package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.UserGroup;
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
     * @param openId
     * @param groupId
     * @return
     */
    UserGroup findGroup(@Param("openId") String openId,
                          @Param("groupId") String groupId);

    /**
     * 接口描述：查询群列表
     * @param openId
     * @param share    分享来源（1-本人分享，2-他人分享）
     * @return
     */
    List<UserGroup> findUserGroupList(@Param("openId") String openId,
                                      @Param("share") String share);


    /**
     * 接口描述：添加红点消息提示
     * @param groupId
     * @param openId
     * @return
     */
    int hintOthers(@Param("groupId") String groupId,
                   @Param("openId") String openId);

}
