package com.zimuka.peers.controller;

import com.zimuka.peers.dao.User;
import com.zimuka.peers.dto.AjaxResultDTO;
import com.zimuka.peers.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping("findOneById")
    @ResponseBody
    public AjaxResultDTO findOneById(Integer id, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        User user = userService.findOneById(id);
        logger.info("查询id:" + id);
        return AjaxResultDTO.success(user);
    }
}
