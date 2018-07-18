package com.zimuka.peers.controller;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dto.AjaxResultDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.service.UserCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userCard")
public class UserCardController {

    private static final Logger logger = LoggerFactory.getLogger(UserCardController.class);

    @Resource
    private UserCardService userCardService;

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResultDTO saveOrUpdate(UserCard userCard) {
        try {
            userCardService.saveOrUpdate(userCard);
            return AjaxResultDTO.success();
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("修改名片异常：", e);
            return AjaxResultDTO.failed("修改名片异常");
        }
    }

    @RequestMapping("/findOne")
    @ResponseBody
    public AjaxResultDTO findOne(Integer userId) {
        try {
            UserCard userCard = userCardService.findOneByUser(userId);
            return AjaxResultDTO.success(userCard);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("查询用户名片异常：", e);
            return AjaxResultDTO.failed("查询用户名片异常");
        }
    }
}
