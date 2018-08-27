package com.eqxuan.peers.service.impl;

import com.eqxuan.peers.dao.UserFromId;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.mapper.UserFromIdMapper;
import com.eqxuan.peers.service.UserFromIdService;
import com.eqxuan.peers.vo.UserFromIdsVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: zheng guangjing.
 * @date: 2018/8/14 16:46
 * @description: description
 */
@Service
public class UserFromIdServiceImpl implements UserFromIdService {

    @Resource
    private UserFromIdMapper userFromIdMapper;

    /**
     *  接口描述：收集用户fromIds
     * @param userFromIdsVO
     */
    @Override
    public void save(UserFromIdsVO userFromIdsVO) {
        if (StringUtils.isEmpty(userFromIdsVO.getOpenId())) {
            throw new PeerProjectException("参数异常");
        }

        if (null == userFromIdsVO.getFromIds() || "".equals(userFromIdsVO.getFromIds()) || 0 == userFromIdsVO.getFromIds().size()) {
            throw new PeerProjectException("FromId为空");
        }

        UserFromId userFromId = new UserFromId();
        int rows;
        userFromId.setOpenId(userFromIdsVO.getOpenId());
        for (String formId : userFromIdsVO.getFromIds()) {

            userFromId.setFromId(formId);
            userFromId.setCtTime(new Date());
            rows = userFromIdMapper.save(userFromId);
            if (1 != rows) {
                throw new PeerProjectException("保存用户表单失败");
            }
        }
    }
}
