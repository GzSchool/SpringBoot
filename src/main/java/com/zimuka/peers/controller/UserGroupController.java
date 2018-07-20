package com.zimuka.peers.controller;

import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.dto.AjaxResultDTO;
import com.zimuka.peers.dto.CardsOnGroupDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/userGroup")
public class UserGroupController {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Resource
    private UserGroupService userGroupService;

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResultDTO saveOrUpdate(UserGroup userGroup, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            userGroupService.saveOrUpdate(userGroup);
            return AjaxResultDTO.success();
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【群绑定异常】：{}", e);
            return AjaxResultDTO.failed("群分享异常");
        }
    }

    @RequestMapping("/findGroupCards")
    @ResponseBody
    public AjaxResultDTO findGroupCards(String openId, String groupId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<CardsOnGroupDTO> cardsOnGroupDTOS = userGroupService.findCardsOnGroupByOpenId(openId, groupId);
            return AjaxResultDTO.success(cardsOnGroupDTOS);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询群名片列表异常】：{}", e);
            return AjaxResultDTO.failed("查询群名片列表异常");
        }
    }
}
