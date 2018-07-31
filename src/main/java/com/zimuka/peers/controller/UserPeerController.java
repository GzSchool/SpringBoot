package com.zimuka.peers.controller;

import com.zimuka.peers.dao.UserCard;
import com.zimuka.peers.dao.UserPeer;
import com.zimuka.peers.dto.AjaxResultDTO;
import com.zimuka.peers.dto.PageDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.service.UserPeerService;
import com.zimuka.peers.vo.CreatePeersVO;
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
     * 查询用户名片夹
     * @param openId
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
}
