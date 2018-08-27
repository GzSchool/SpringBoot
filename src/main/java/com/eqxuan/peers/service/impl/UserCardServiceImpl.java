package com.eqxuan.peers.service.impl;

import com.eqxuan.peers.dao.User;
import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.mapper.UserCardMapper;
import com.eqxuan.peers.mapper.UserMapper;
import com.eqxuan.peers.service.UserCardService;
import com.eqxuan.peers.service.WxTemplateService;
import com.eqxuan.peers.service.cache.CacheManager;
import com.eqxuan.peers.utils.DateUtil;
import com.eqxuan.peers.exception.PeerProjectException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserCardServiceImpl implements UserCardService {

    private static final Logger logger = LoggerFactory.getLogger(UserCardServiceImpl.class);

    @Resource
    private UserCardMapper userCardMapper;

    @Resource
    private WxTemplateService wxTemplateService;

    @Resource
    private CacheManager cacheManager;

    @Resource
    private UserMapper userMapper;

    /**
     * 接口描述：根据用户标识查询名片信息
     * @param id
     * @return
     */
    @Override
    public UserCard findOneById(String id) {

        if (StringUtils.isEmpty(id)) {
            throw new PeerProjectException("请选择您要查看的名片");
        }
        UserCard userCard = cacheManager.getUserCardById(id);
        if (null == userCard) {
            throw new PeerProjectException("您还未添加名片");
        }
        return userCard;
    }

    /**
     * 接口描述：根据入参查询名片信息
     * @param userCard
     * @return
     */
    @Override
    public List<UserCard> findCardByParam(UserCard userCard) {

        List<UserCard> userCardList = userCardMapper.findCardByParam(userCard);

        return userCardList;
    }

    /**
     * 接口描述：在当前用户的名片夹进行搜索操作
     * @param param
     * @param openId
     * @return
     */
    @Override
    public List<ReturnCardDTO> findAllByPeerAndParam(String param, String openId) {

        if (StringUtils.isEmpty(param)) {
            return null;
        }

        if (StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("用户未登陆");
        }

        List<ReturnCardDTO> returnCardDTOS = userCardMapper.findAllByPeerAndParam(param, openId);

        return returnCardDTOS;
    }

    /**
     * 接口描述：添加用户名片
     * @param userCard
     * @return
     */
    @Override
    public int save(UserCard userCard) {

        if (StringUtils.isEmpty(userCard.getPrepare()) || StringUtils.isEmpty(userCard.getUserPhone()) || StringUtils.isEmpty(userCard.getUserCompany()) || StringUtils.isEmpty(userCard.getUserCity()) || StringUtils.isEmpty(userCard.getUserJob()) || StringUtils.isEmpty(userCard.getUserIndustry())){
            throw new PeerProjectException("参数缺失");
        }

        if (StringUtils.isEmpty(userCard.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        User checkUser = userMapper.findOneByOpenId(userCard.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }
        UserCard queryUserCard = new UserCard();
        queryUserCard.setOpenId(userCard.getOpenId());
        queryUserCard.setDelFlag(1);
        List<UserCard> userCardList = userCardMapper.findCardByParam(queryUserCard);
        // 单人创建名片最大数
        int cardNum = 5;
        if (userCardList.size() >= cardNum) {
            throw new PeerProjectException("单人创建名片不可超过5张");
        }
        logger.info("【添加时获取prepare】：{}", userCard.getPrepare());

        userCard.setCtTime(new Date());
        UserCard saveUserCard = new UserCard();
        BeanUtils.copyProperties(userCard, saveUserCard);
        int rows = userCardMapper.save(saveUserCard);
        if (1 != rows) {
            throw new PeerProjectException("保存名片失败");
        }
        //添加到缓存
        cacheManager.cacheUserCard(saveUserCard);
        // 消息模板推送
        JSONObject jsonObject = wxTemplateService.makeCardSuccess(userCard.getOpenId(), userCard.getFormId(), DateUtil.dateToString(saveUserCard.getCtTime()));
        logger.info("【模板消息推送】：{}", jsonObject);

        return saveUserCard.getId();
    }

    /**
     * 接口描述：修改用户接口
     * @param userCard
     */
    @Override
    public void update(UserCard userCard) {

        if (StringUtils.isEmpty(userCard.getPrepare()) || StringUtils.isEmpty(userCard.getUserPhone()) || StringUtils.isEmpty(userCard.getUserCompany()) || StringUtils.isEmpty(userCard.getUserCity()) || StringUtils.isEmpty(userCard.getUserJob()) || StringUtils.isEmpty(userCard.getUserIndustry())){
            throw new PeerProjectException("参数缺失");
        }

        if (StringUtils.isEmpty(userCard.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        if (null == userCard.getId() || "".equals(userCard.getId())) {
            throw new PeerProjectException("请选择要修改的名片");
        }

        User checkUser = userMapper.findOneByOpenId(userCard.getOpenId());
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        UserCard checkUserCard = userCardMapper.findById(userCard.getId());
        if (null == checkUserCard) {
            throw new PeerProjectException("该名片不存在");
        }

        if (!checkUserCard.getOpenId().equals(userCard.getOpenId())) {
            throw new PeerProjectException("该名片不属于你，不能修改");
        }

        logger.info("【修改时获取prepare】：{}", userCard.getPrepare());

        userCard.setUpTime(new Date());
        UserCard updateUserCard = new UserCard();
        BeanUtils.copyProperties(userCard, updateUserCard);
        int rows = userCardMapper.update(updateUserCard);
        if (1 != rows) {
            throw new PeerProjectException("修改名片失败");
        }
        //添加到缓存
        cacheManager.cacheUserCard(updateUserCard);
    }

    /**
     * 接口描述：查询个人名片列表
     * @param openId
     * @return
     */
    @Override
    public List<ReturnCardDTO> findCardListByOpenId(String openId) {
        if (StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("参数缺失");
        }

        User checkUser = userMapper.findOneByOpenId(openId);
        if (null == checkUser) {
            throw new PeerProjectException("用户未注册");
        }

        List<ReturnCardDTO> returnCardDTOS = userCardMapper.findCardListByOpenId(openId);
        return returnCardDTOS;
    }
}
