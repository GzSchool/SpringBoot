package com.eqxuan.peers.mapper;

import com.eqxuan.peers.dao.UserGroup;
import com.eqxuan.peers.dto.GroupNoSaveNumDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMapper {

    int save(UserGroup userGroup);

    int update(UserGroup userGroup);

    UserGroup findOneById(@Param("openId") String openId,
                          @Param("groupId") String groupId);

    List<UserGroup> findUserGroupByParam(UserGroup userGroup);

    List<ReturnCardDTO> findCardsOnGroupByOpenId(@Param("openId") String openId,
                                                 @Param("groupId") String groupId);

    List<ReturnCardDTO> findAllGroupCardByParam(@Param("groupId") String groupId,
                                                @Param("openId") String openId,
                                                @Param("param") String param);

    List<GroupNoSaveNumDTO> countByNoSave(@Param("groupId") String groupId,
                                          @Param("openId") String openId);

    List<String> getNineBeforeByGroupId(String groupId);
}
