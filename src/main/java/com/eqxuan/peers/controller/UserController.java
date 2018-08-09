package com.eqxuan.peers.controller;

import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.dto.AuthorizeDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 收集用户信息
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 用户授权，保存用户信息
     * @param code
     * @param response
     * @return
     */
    @RequestMapping("/userAuthor")
    @ResponseBody
    public AjaxResultDTO userAuthor(String code, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            AuthorizeDTO authorizeDTO = userService.get3rdsession(code);
            return AjaxResultDTO.success(authorizeDTO);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【用户授权异常】：{}", e);
            return AjaxResultDTO.failed("用户授权异常");
        }
    }
}
