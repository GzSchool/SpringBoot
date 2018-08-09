package com.eqxuan.peers.controller;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.UserCardService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
@Api(description = "UserCardController", tags = "用户卡片相关接口 @郑光景")
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
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存用户卡片信息")
    public AjaxResultDTO saveOrUpdate(
            @RequestBody @ApiParam(name = "用户卡片对象",value = "传入Json格式的值", required = true) UserCard userCard,
            HttpServletResponse response) {
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
    @RequestMapping(value = "/findOneByOpenId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据openId获取用户卡片信息")
    @ApiImplicitParam(name = "openId", value = "微信用户唯一标识", required = true, dataType = "String", paramType = "")
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
