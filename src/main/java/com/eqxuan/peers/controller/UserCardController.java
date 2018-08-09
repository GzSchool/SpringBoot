package com.eqxuan.peers.controller;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.UserCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 用户名片操作
 */
@RestController
@RequestMapping("/userCard")
public class UserCardController {

    private static final Logger logger = LoggerFactory.getLogger(UserCardController.class);

    @Resource
    private UserCardService userCardService;

    /**
     * 增加，修改名片
     * @param userCard
     * @param response
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResultDTO saveOrUpdate(@RequestBody UserCard userCard, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            userCardService.saveOrUpdate(userCard);
            return AjaxResultDTO.success();
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【修改名片异常】：{}", e);
            return AjaxResultDTO.failed("修改名片异常");
        }
    }

    /**
     * 查询指定用户名片
     * @param openId
     * @param response
     * @return
     */
    @RequestMapping("/findOneByOpenId")
    @ResponseBody
    public AjaxResultDTO findOneByOpenId(String openId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            UserCard userCard = userCardService.findOneByOpenId(openId);
            return AjaxResultDTO.success(userCard);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【查询名片异常】：{}", e);
            return AjaxResultDTO.failed("查询名片异常");
        }
    }

    /**
     * 动态查询
     * @param userCard
     * @param response
     * @return
     */
    @RequestMapping("/findCardByParam")
    @ResponseBody
    public AjaxResultDTO findCardByParam(UserCard userCard, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<UserCard> userCardList = userCardService.findCardByParam(userCard);
            return AjaxResultDTO.success(userCardList);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询Param名片异常】:{}", e);
            return AjaxResultDTO.failed("查询名片异常");
        }
    }

    /**
     * 模糊搜索（全表搜索）
     * @param param
     * @param pageNum
     * @param pageSize
     * @param response
     * @return
     */
    @RequestMapping("/findAllByParam")
    @ResponseBody
    public AjaxResultDTO findAllByParam(String param, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            PageDTO pageDTO = userCardService.findAllByParam(param, pageNum, pageSize);
            return AjaxResultDTO.success(pageDTO);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【搜索名片异常】：{}", e);
            return AjaxResultDTO.failed("搜索名片异常");
        }
    }

    @RequestMapping("/findAllByPeerAndParam")
    @ResponseBody
    public AjaxResultDTO findAllByPeerAndParam(String param, String openId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<ReturnCardDTO> returnCardDTOS = userCardService.findAllByPeerAndParam(param, openId);
            return AjaxResultDTO.success(returnCardDTOS);
        }catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        }catch (Exception e) {
            logger.error("【搜索名片异常】：{}", e);
            return AjaxResultDTO.failed("搜索名片异常");
        }
    }
}