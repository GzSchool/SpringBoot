package com.zimuka.peers.service.impl;

import com.zimuka.peers.configBeans.MiniAppBean;
import com.zimuka.peers.dao.User;
import com.zimuka.peers.dto.AuthorizeDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserMapper;
import com.zimuka.peers.service.WxCusInfoService;
import com.zimuka.peers.utils.WxTemplateUtil;
import com.zimuka.peers.vo.WechatOpenId;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WxCusInfoServiceImpl implements WxCusInfoService {

    private static final Logger logger = LoggerFactory.getLogger(WxCusInfoServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MiniAppBean miniAppBean;

    @Override
    public AuthorizeDTO get3rdsession(String code) {
        logger.info("【获取code】：" + code);
        if (StringUtils.isEmpty(code)) {
            throw new PeerProjectException("code参数异常");
        }

        AuthorizeDTO authorizeDTO = new AuthorizeDTO();

        //传入AppId和AppSecret 获取 openId和sessionKey
        WechatOpenId wechatOpenId = WxTemplateUtil.getOpenIdAndSessionKey(miniAppBean.getAppId(), miniAppBean.getAppSecret(), code);
        logger.info("【获取openId】：" + wechatOpenId.getOpenId());
        logger.info("【获取sessionKey】：" + wechatOpenId.getSessionKey());

        String openSession = DigestUtils.md5Hex(wechatOpenId.getOpenId() + wechatOpenId.getSessionKey());

        User checkUser = userMapper.findOneByOpenId(wechatOpenId.getOpenId());
        User saveUser;
        if (null == checkUser) {
            saveUser = new User();
            saveUser.setOpenid(wechatOpenId.getOpenId());
            saveUser.setSessionKey(wechatOpenId.getSessionKey());
            saveUser.setOpenSession(openSession);
            userMapper.saveUser(saveUser);

            authorizeDTO.setOpenId(wechatOpenId.getOpenId());
            authorizeDTO.setOpenSession(openSession);
            return authorizeDTO;
        } else {
            saveUser = new User();
            saveUser.setSessionKey(wechatOpenId.getSessionKey());
            saveUser.setOpenSession(openSession);
            userMapper.updateUser(saveUser);

            authorizeDTO.setOpenId(wechatOpenId.getOpenId());
            authorizeDTO.setOpenSession(openSession);
            return authorizeDTO;
        }
    }
}
