package com.eqxuan.peers.controller;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.AjaxResultDTO;
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
 * @author: zheng guangjing.
 * @Date: 2018/8/13 16:05
 * @Description: 用户名片相关接口
 */
@RestController
@RequestMapping("/userCard")
@Api(tags = "用户名片相关接口 @郑光景", description = "UserCardController")
public class UserCardController {

    private static final Logger logger = LoggerFactory.getLogger(UserCardController.class);

    @Resource
    private UserCardService userCardService;

    @PostMapping("/saveOrUpdate")
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

    @GetMapping("/findOneByOpenId")
    @ResponseBody
    @ApiOperation(value = "根据openId获取用户卡片信息")
    @ApiImplicitParam(name = "openId", value = "微信用户唯一标识", required = true, dataType = "String")
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

    @GetMapping("/findCardByParam")
    @ResponseBody
    @ApiOperation(value = "根据前端传递信息，查询指定名片")
    public AjaxResultDTO findCardByParam(
            @ApiParam(name = "前端传递信息", value = "传入的JSON值", required = true) UserCard userCard,
            HttpServletResponse response) {
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

    @GetMapping("/findAllByPeerAndParam")
    @ResponseBody
    @ApiOperation(value = "模糊搜索当前用户的名片夹信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "搜索参数", required = false, dataType = "String", paramType = ""),
            @ApiImplicitParam(name = "openId", value = "当前用户唯一标识", required = true, dataType = "String", paramType = "")
    })
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