package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.dto.CardsOnGroupDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMapper {

    int save(UserGroup userGroup);

    int update(UserGroup userGroup);

    UserGroup findOneById(@Param("openId") String openId,
                          @Param("groupId") String groupId);

    List<UserGroup> findUserGroupByParam(UserGroup userGroup);

    List<UserGroup> findUsersByGroupId(@Param("groupId") String groupId,
                                       @Param("openId") String openId);

    List<CardsOnGroupDTO> findCardsOnGroupByOpenId(@Param("openId") String openId,
                                                   @Param("groupId") String groupId);

}
