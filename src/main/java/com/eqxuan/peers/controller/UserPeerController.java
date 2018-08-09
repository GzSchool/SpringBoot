package com.eqxuan.peers.controller;

import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.UserPeerService;
import com.eqxuan.peers.vo.CreatePeersVO;
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
 * @Description: 用户同行信息操作
 */
@RestController
@RequestMapping("/userPeer")
public class UserPeerController {

    private static final Logger logger = LoggerFactory.getLogger(UserPeerController.class);

    @Resource
    private UserPeerService userPeerService;

    /**
     * 保存同行名片，修改保存名片
     * @param createPeersVO
     * @param response
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResultDTO saveOrUpdate(@RequestBody CreatePeersVO createPeersVO, HttpServletResponse response) {
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

    /**
     * 查询用户名片夹（含分页）
     * @param openId
     * @param pageNum
     * @param pageSize
     * @param response
     * @return
     */
    @RequestMapping("/findAllByOpenId")
    @ResponseBody
    public AjaxResultDTO findAllByOpenId(String openId, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            PageDTO pageDTO = userPeerService.findAllByOpenId(openId, pageNum, pageSize);
            return AjaxResultDTO.success(pageDTO);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询用户名片夹异常】：{}", e);
            return AjaxResultDTO.failed("查询名片夹异常");
        }
    }

    @RequestMapping("/findAllPeerByOpenId")
    @ResponseBody
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

    /**
     * 检验当前名片是否被当前用户保存
     * @param openId
     * @param cardId
     * @param response
     * @return
     */
    @RequestMapping("/checkSave")
    @ResponseBody
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
}
