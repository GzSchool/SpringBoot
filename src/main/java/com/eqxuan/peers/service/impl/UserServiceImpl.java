package com.eqxuan.peers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eqxuan.peers.dao.User;
import com.eqxuan.peers.dto.AuthorizeDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.mapper.UserMapper;
import com.eqxuan.peers.service.UserService;
import com.eqxuan.peers.service.cache.CacheManager;
import com.eqxuan.peers.utils.WxDecipherUtil;
import com.eqxuan.peers.utils.WxTemplateUtil;
import com.eqxuan.peers.vo.WechatOpenId;
import com.eqxuan.peers.config.MiniAppBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MiniAppBean miniAppBean;

    @Autowired
    private CacheManager cacheManager;

    @Resource
    private WxDecipherUtil wxDecipherUtil;

    @Override
    public AuthorizeDTO get3rdsession(String code) {

        logger.info("【获取code】：" + code);
        if (StringUtils.isEmpty(code)) {
            throw new PeerProjectException("code不能为空");
        }

        AuthorizeDTO authorizeDTO = new AuthorizeDTO();
        //传入AppId和AppSecret
        WechatOpenId wechatOpenId = WxTemplateUtil.getOpenIdAndSessionKey(miniAppBean.getAppId(), miniAppBean.getAppSecret(), code);
        logger.info("【获取openId】：" + wechatOpenId.getOpenId());
        logger.info("【获取sessionKey】：" + wechatOpenId.getSessionKey());
        if (StringUtils.isEmpty(wechatOpenId.getOpenId())) {
            throw new PeerProjectException("获取openId异常");
        }
        String openSession = DigestUtils.md5Hex(wechatOpenId.getOpenId() + wechatOpenId.getSessionKey());

        User checkUser = userMapper.findOneByOpenId(wechatOpenId.getOpenId());
        User saveUser = new User();
        int rows;
        if (null == checkUser) {
            saveUser.setOpenId(wechatOpenId.getOpenId());
            saveUser.setSessionKey(wechatOpenId.getSessionKey());
            saveUser.setOpenSession(openSession);
            saveUser.setCtTime(new Date());
            rows = userMapper.save(saveUser);
            if (1 != rows) {
                throw new PeerProjectException("添加用户信息失败");
            }

            cacheManager.cacheEmptyUserCard(wechatOpenId.getOpenId());

            authorizeDTO.setOpenId(wechatOpenId.getOpenId());
            authorizeDTO.setOpenSession(openSession);
            return authorizeDTO;
        } else {
            saveUser.setOpenId(wechatOpenId.getOpenId());
            saveUser.setSessionKey(wechatOpenId.getSessionKey());
            saveUser.setOpenSession(openSession);
            saveUser.setId(checkUser.getId());
            saveUser.setUpTime(new Date());
            rows = userMapper.update(saveUser);
            if (1 != rows) {
                throw new PeerProjectException("修改用户信息失败");
            }

            authorizeDTO.setOpenId(wechatOpenId.getOpenId());
            authorizeDTO.setOpenSession(openSession);
            return authorizeDTO;
        }
    }

    @Override
    public String getUserPhone(String openId, String iv, String encryptedData) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(iv) || StringUtils.isEmpty(encryptedData)) {
            throw new PeerProjectException("参数缺失");
        }

        User checkUser = userMapper.findOneByOpenId(openId);
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        JSONObject jsonObject = wxDecipherUtil.getDecipherInfo(encryptedData, checkUser.getSessionKey(), iv);
        logger.debug("-------------------------" + jsonObject);
        if (null == jsonObject) {
            throw new PeerProjectException("解密信息失败");
        }
        String phoneNumber = jsonObject.getString("phoneNumber");
        return phoneNumber;
    }
}
