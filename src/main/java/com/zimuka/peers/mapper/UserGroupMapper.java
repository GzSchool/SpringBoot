package com.zimuka.peers.mapper;

import com.zimuka.peers.dao.UserGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMapper {

    int save(UserGroup userGroup);

    int update(UserGroup userGroup);

    UserGroup findOneById(@Param("userId") Integer userId,
                          @Param("groupId") String groupId);

    List<UserGroup> findAllByUserGroup(UserGroup userGroup);
}
