package com.zimuka.peers.controller;

import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.dto.AjaxResultDTO;
import com.zimuka.peers.dto.UserPeerDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.service.UserPeerService;
import com.zimuka.peers.vo.UserPeerSaveVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/userPeer")
public class UserPeerController {

    private static final Logger logger = LoggerFactory.getLogger(UserPeerController.class);

    @Resource
    private UserPeerService userPeerService;

    @RequestMapping("/findAll")
    @ResponseBody
    public AjaxResultDTO findAll(String openId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<UserPeerDTO> userPeerDTOS = userPeerService.findAll(openId);
            return AjaxResultDTO.success(userPeerDTOS);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询已有同行信息异常】：", e);
            return AjaxResultDTO.failed("查询已有同行信息异常");
        }
    }

    @RequestMapping("/findAllByParam")
    @ResponseBody
    public AjaxResultDTO findAllByParam(String param, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<UserPeerDTO> userPeerDTOS = userPeerService.findAllByParam(param);
            return AjaxResultDTO.success(userPeerDTOS);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【搜索名片异常】：", e);
            return AjaxResultDTO.failed("搜索名片异常");
        }
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResultDTO saveOrUpdate(UserPeerSaveVO userPeerSaveVO, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            userPeerService.saveOrUpdate(userPeerSaveVO);
            return AjaxResultDTO.success();
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【操作名片异常】：", e);
            return AjaxResultDTO.failed("操作名片异常");
        }
    }

    @RequestMapping("/findUserPeerByParam")
    @ResponseBody
    public AjaxResultDTO findUserPeerByParam(UserPeer userPeer, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<UserPeer> userPeerList = userPeerService.findUserPeerByParam(userPeer);
            return AjaxResultDTO.success(userPeerList);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e){
            logger.error("【查询名片ByParam异常】：{}", e);
            return AjaxResultDTO.failed("查询名片异常");
        }
    }
}
