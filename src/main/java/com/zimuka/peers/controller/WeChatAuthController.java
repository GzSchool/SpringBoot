package com.zimuka.peers.controller;

import com.zimuka.peers.dto.AjaxResultDTO;
import com.zimuka.peers.dto.AuthorizeDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.service.WxCusInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/weChatAuth")
public class WeChatAuthController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatAuthController.class);

    @Resource
    private WxCusInfoService wxCusInfoService;

    /**
     * 小程序授权
     * @param jsCode
     * @return
     */
    @RequestMapping("/authorize")
    @ResponseBody
    public AjaxResultDTO authorize(String jsCode, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            AuthorizeDTO authorizeDTO = wxCusInfoService.get3rdsession(jsCode);
            return AjaxResultDTO.success(authorizeDTO);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【小程序授权异常】：{}", e);
            return  AjaxResultDTO.failed("授权异常");
        }
    }
}
