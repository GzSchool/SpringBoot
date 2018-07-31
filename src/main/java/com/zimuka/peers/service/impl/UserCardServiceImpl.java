package com.zimuka.peers.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimuka.peers.configBeans.MiniAppBean;
import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dto.PageDTO;
import com.zimuka.peers.dto.ReturnCardDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.mapper.UserCardMapper;
import com.zimuka.peers.service.UserCardService;
import com.zimuka.peers.service.WxTemplateService;
import com.zimuka.peers.utils.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserCardServiceImpl implements UserCardService {

    private static final Logger logger = LoggerFactory.getLogger(UserCardServiceImpl.class);

    @Resource
    private UserCardMapper userCardMapper;

    @Autowired
    private MiniAppBean miniAppBean;

    @Resource
    private WxTemplateService wxTemplateService;

    @Override
    public void saveOrUpdate(UserCard userCard) {

        if (StringUtils.isEmpty(userCard.getUserWechat()) || StringUtils.isEmpty(userCard.getUserCompany()) || StringUtils.isEmpty(userCard.getUserCity()) || StringUtils.isEmpty(userCard.getUserJob()) || StringUtils.isEmpty(userCard.getUserIndustry())){
            throw new PeerProjectException("必填字段不可为空");
        }

        if (null == userCard.getOpenId() || "".equals(userCard.getOpenId())) {
            throw new PeerProjectException("用户未登陆");
        }

        UserCard checkUserCard = userCardMapper.findOneByOpenId(userCard.getOpenId());
        UserCard saveUserCard = new UserCard();
        userCard.setAppId(miniAppBean.getAppId());
        int rows;
        if (null == checkUserCard) {
            userCard.setCtTime(new Date());
            BeanUtils.copyProperties(userCard, saveUserCard);
            rows = userCardMapper.save(saveUserCard);
            if (1 != rows) {
                throw new PeerProjectException("添加名片失败");
            }

            //TODO 消息模板推送
//            JSONObject jsonObject = wxTemplateService.makeCardSuccess(userCard.getOpenId(), userCard.getFormId(), DateUtil.dateToString(saveUserCard.getCtTime()));
//
//            logger.info("【模板消息推送】：{}", jsonObject);

        } else {

            if (null == userCard.getId() || "".equals(userCard.getId())) {
                throw new PeerProjectException("选择您要修改的名片");
            }

            if (checkUserCard.getId().intValue() != userCard.getId().intValue()) {
                throw new PeerProjectException("该名片不属于你，不能修改");
            }

            userCard.setUpTime(new Date());
            BeanUtils.copyProperties(userCard, saveUserCard);
            rows = userCardMapper.update(saveUserCard);
            if (1 != rows) {
                throw new PeerProjectException("修改名片失败");
            }
        }
    }

    @Override
    public UserCard findOneByOpenId(String openId) {

        if (StringUtils.isEmpty(openId)) {
            throw new PeerProjectException("用户未登陆");
        }

        UserCard userCard = userCardMapper.findOneByOpenId(openId);
        if (null == userCard) {
            throw new PeerProjectException("您还未添加名片");
        }
        return userCard;
    }

    @Override
    public List<UserCard> findCardByParam(UserCard userCard) {

        List<UserCard> userCardList = userCardMapper.findCardByParam(userCard);

        return userCardList;
    }

    @Override
    public PageDTO findAllByParam(String param, Integer pageNum, Integer pageSize) {

        //分页查询（插件）pageNum当前页，将此方法放置在需要分页的SQL查询之前，即可对其分页，只对其最近的一条有效
        PageHelper.startPage(pageNum, pageSize);
        List<UserCard> userCardList = userCardMapper.findAllByParam(param);

        PageInfo<UserCard> pageInfo = new PageInfo<UserCard>(userCardList);
        List<ReturnCardDTO> returnCardDTOS = new ArrayList<ReturnCardDTO>(pageInfo.getList().size());

        for (UserCard userCard : pageInfo.getList()) {
            ReturnCardDTO returnCardDTO = new ReturnCardDTO();
            BeanUtils.copyProperties(userCard, returnCardDTO);
            returnCardDTOS.add(returnCardDTO);
        }

        PageDTO pageDTO = new PageDTO();
        pageDTO.setData(returnCardDTOS);
        pageDTO.setPages(pageInfo.getPages());
        pageDTO.setTotal(pageInfo.getTotal());

        return pageDTO;
    }
}
