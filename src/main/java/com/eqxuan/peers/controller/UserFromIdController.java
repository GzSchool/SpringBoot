package com.eqxuan.peers.controller;

import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.UserFromIdService;
import com.eqxuan.peers.vo.UserFromIdsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: zheng guangjing.
 * @date: 2018/8/14 17:04
 * @description: description
 */
@RestController
@RequestMapping("/userFromId")
@Api(tags = "收集用户fromIds相关接口 @郑光景", description = "UserFromIdController")
public class UserFromIdController {

    private static final Logger logger = LoggerFactory.getLogger(UserFromIdController.class);

    @Resource
    private UserFromIdService userFromIdService;

    @PostMapping("/save")
    @ResponseBody
    @ApiOperation(value = "收集用户fromIds")
    public AjaxResultDTO save(@RequestBody @ApiParam(name = "收集用户fromIds对象", value = "传入的JSON值", required = true) UserFromIdsVO userFromIdsVO,
                              HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            userFromIdService.save(userFromIdsVO);
            return AjaxResultDTO.success();
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【保存用户表单异常】：{}", e);
            return AjaxResultDTO.failed("保存用户表单异常");
        }
    }
}
