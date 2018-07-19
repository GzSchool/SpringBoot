package com.zimuka.peers.service.impl;

import com.zimuka.peers.dao.User;
import com.zimuka.peers.dto.AuthorizeDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.service.UserService;
import com.zimuka.peers.service.WxCusInfoService;
import com.zimuka.peers.utils.WxTemplateUtil;
import com.zimuka.peers.vo.WechatOpenId;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WxCusInfoServiceImpl implements WxCusInfoService {

    private static final Logger logger = LoggerFactory.getLogger(WxCusInfoServiceImpl.class);

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Override
    public AuthorizeDTO get3rdsession(String code) {
        logger.info("【获取code】：" + code);
        if (StringUtils.isEmpty(code)) {
            throw new PeerProjectException("code参数异常");
        }

        AuthorizeDTO authorizeDTO = new AuthorizeDTO();

        //获取AppId和AppSecret
        String AppId = "wx34fefa15f676d944";
        String AppSecret = "f4ce303b50267dc17dc69ae083ae3a98";
        logger.info("【获取AppId】：" + AppId);
        logger.info("【获取AppSecret】：" + AppSecret);
        WechatOpenId wechatOpenId = WxTemplateUtil.getOpenIdAndSessionKey(AppId, AppSecret, code);
        logger.info("【获取openId】：" + wechatOpenId.getOpenId());
        logger.info("【获取sessionKey】：" + wechatOpenId.getSessionKey());

        User checkUser = userMapper.findOneByOpenId(wechatOpenId.getOpenId());
        if (null == checkUser) {
            User saveUser = new User();
            saveUser.setOpenid(wechatOpenId.getOpenId());
            userService.saveUser(saveUser);

            authorizeDTO.setOpenId(wechatOpenId.getOpenId());
            return authorizeDTO;
        } else {
            authorizeDTO.setOpenId(wechatOpenId.getOpenId());
            return authorizeDTO;
        }
    }
}
