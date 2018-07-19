package com.zimuka.peers.controller;

import com.zimuka.peers.dao.UserGroup;
import com.zimuka.peers.dto.AjaxResultDTO;
import com.zimuka.peers.exception.PeerProjectException;
import com.zimuka.peers.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/userGroup")
public class UserGroupController {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Resource
    private UserGroupService userGroupService;

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResultDTO saveOrUpdate(UserGroup userGroup, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            userGroupService.saveOrUpdate(userGroup);
            return AjaxResultDTO.success();
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【修改群列表异常】：{}", e);
            return AjaxResultDTO.failed("修改群列表异常");
        }
    }

    @RequestMapping("/findAllByUserGroup")
    @ResponseBody
    public AjaxResultDTO findAllByUserGroup(UserGroup userGroup, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<UserGroup> userGroupList = userGroupService.findAllByUserGroup(userGroup);
            return AjaxResultDTO.success(userGroupList);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询群组异常】：{}", e);
            return AjaxResultDTO.failed("查询群组异常");
        }
    }
}
