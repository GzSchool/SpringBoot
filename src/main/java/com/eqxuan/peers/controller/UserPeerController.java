package com.eqxuan.peers.controller;

import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.UserPeerService;
import com.eqxuan.peers.vo.CreatePeersVO;
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
 * @Description: 用户同行信息操作
 */
@RestController
@RequestMapping("/userPeer")
@Api(tags = "用户同行信息相关接口 @郑光景", description = "UserPeerController")
public class UserPeerController {

    private static final Logger logger = LoggerFactory.getLogger(UserPeerController.class);

    @Resource
    private UserPeerService userPeerService;

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    @ApiOperation(value = "用户保存，修改同行名片")
    public AjaxResultDTO saveOrUpdate(
            @RequestBody @ApiParam(name = "用户同行对象", value = "传入的JSON值", required = true) CreatePeersVO createPeersVO,
            HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            userPeerService.saveOrUpdate(createPeersVO);
            return AjaxResultDTO.success();
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【修改用户同行名片异常】：{}", e);
            return AjaxResultDTO.failed("修改用户同行名片异常");
        }
    }

    @GetMapping("/findAllPeerByOpenId")
    @ResponseBody
    @ApiOperation(value = "返回当前用户的名片夹信息")
    @ApiImplicitParam(name = "openId", value = "当前用户唯一标识", required = true, dataType = "String")
    public AjaxResultDTO findAllPeerByOpenId(String openId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<ReturnCardDTO> returnCardDTOS = userPeerService.findAllPeerByOpenId(openId);
            return AjaxResultDTO.success(returnCardDTOS);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询用户名片夹异常】：{}", e);
            return AjaxResultDTO.failed("查询用户名片夹异常");
        }
    }

    @GetMapping("/checkSave")
    @ResponseBody
    @ApiOperation(value = "检验当前名片是否被当前用户保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "当前用户唯一标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cardId", value = "当前名片ID", required = true, dataType = "String")
    })
    public AjaxResultDTO checkSave(String openId, String cardId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            return AjaxResultDTO.success(userPeerService.checkSave(openId, cardId));
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【检验当前用户是否绑定名片异常】：{}", e);
            return AjaxResultDTO.failed("检验当前用户是否绑定名片异常");
        }
    }

    @RequestMapping(value = "/addRemark", method = RequestMethod.POST)
    @ApiOperation(value = "为同行添加备注信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "微信用户唯一标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cardId", value = "同行名片ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "同行备注", required = true, dataType = "String")
    })
    public AjaxResultDTO addRemark(String openId, String cardId, String remark, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            return AjaxResultDTO.success(userPeerService.addRemark(openId, cardId, remark));
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【检验当前用户是否绑定名片异常】：{}", e);
            return AjaxResultDTO.failed("检验当前用户是否绑定名片异常");
        }

    }

    @RequestMapping(value = "/getPeerInfo", method = RequestMethod.GET)
    @ApiOperation(value = "获取同行名片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "当前用户唯一标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cardId", value = "同行名片ID", required = true, dataType = "String")
    })
    public AjaxResultDTO getPeerInfo(String openId, String cardId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            return AjaxResultDTO.success(userPeerService.getPeerInfo(openId, cardId));
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【检验当前用户是否绑定名片异常】：{}", e);
            return AjaxResultDTO.failed("检验当前用户是否绑定名片异常");
        }

    }
}