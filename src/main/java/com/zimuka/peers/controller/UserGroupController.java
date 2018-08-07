package com.zimuka.peers.controller;

import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.dto.AjaxResultDTO;
import com.zimuka.peers.dto.PageDTO;
import com.zimuka.peers.dto.ReturnCardDTO;
import com.zimuka.peers.dto.ReturnGroupDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 添加用户群列表
     * @param userGroup
     * @param response
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResultDTO saveOrUpdate(@RequestBody UserGroup userGroup, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            String returnGroupId = userGroupService.saveOrUpdate(userGroup);
            return AjaxResultDTO.success(returnGroupId);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【群绑定异常】：{}", e);
            return AjaxResultDTO.failed("群分享异常");
        }
    }

    /**
     * 查询指定群中，不包含当前用户的所有名片（含分页）
     * @param openId
     * @param groupId
     * @param pageNum
     * @param pageSize
     * @param response
     * @return
     */
    @RequestMapping("/findGroupCards")
    @ResponseBody
    public AjaxResultDTO findGroupCards(String openId, String groupId, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            PageDTO pageDTO = userGroupService.findCardsOnGroupByOpenId(openId, groupId, pageNum, pageSize);
            return AjaxResultDTO.success(pageDTO);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询群名片列表异常】：{}", e);
            return AjaxResultDTO.failed("查询群名片列表异常");
        }
    }

    /**
     * 根据入参查询
     * @param userGroup
     * @param response
     * @return
     */
    @RequestMapping("/findUserGroupByParam")
    @ResponseBody
    public AjaxResultDTO findUserGroupByParam(UserGroup userGroup, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<ReturnGroupDTO> returnGroupDTOS = userGroupService.findUserGroupByParam(userGroup);
            return AjaxResultDTO.success(returnGroupDTOS);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询群列表异常】：{}", e);
            return AjaxResultDTO.failed("查询群列表异常");
        }
    }

    /**
     * 群内模糊搜索
     * @param groupId
     * @param openId
     * @param param
     * @param response
     * @return
     */
    @RequestMapping("/findAllGroupCardByParam")
    @ResponseBody
    public AjaxResultDTO findAllGroupCardByParam(String groupId, String openId, String param, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<ReturnCardDTO> returnCardDTOS = userGroupService.findAllGroupCardByParam(groupId, openId, param);
            return AjaxResultDTO.success(returnCardDTOS);
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【参数群查询名片异常】：{}", e);
            return AjaxResultDTO.failed("参数群查询名片异常");
        }
    }

    /**
     * 查询群内除当前用户以外的所有名片
     * @param openId
     * @param groupId
     * @param response
     * @return
     */
    @RequestMapping("/findCardsNoPage")
    @ResponseBody
    public AjaxResultDTO findCardsNoPage(String openId, String groupId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<ReturnCardDTO> returnCardDTOS = userGroupService.findCardsNoPage(openId,groupId);
            return AjaxResultDTO.success(returnCardDTOS);
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【查询群内名片异常】：{}", e);
            return AjaxResultDTO.failed("查询群内名片异常");
        }
    }
}
