package com.eqxuan.peers.controller;

import com.eqxuan.peers.dao.UserGroup;
import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.dto.PageDTO;
import com.eqxuan.peers.dto.ReturnCardDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.UserGroupService;
import com.eqxuan.peers.dto.ReturnGroupDTO;
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
 * @Description: 微信群操作相关接口
 */
@RestController
@RequestMapping("/userGroup")
@Api(tags = "微信群操作相关接口 @郑光景", description = "UserGroupController")
public class UserGroupController {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Resource
    private UserGroupService userGroupService;

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    @ApiOperation(value = "添加用户群列表")
    public AjaxResultDTO saveOrUpdate(
            @RequestBody @ApiParam(name = "用户群对象", value = "传入的JSON值", required = true) UserGroup userGroup,
            HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            String returnGroupId = userGroupService.saveOrUpdate(userGroup);
            return AjaxResultDTO.success(returnGroupId);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【群绑定异常】：{}", e);
            return AjaxResultDTO.failed("群分享异常");
        }
    }

    @GetMapping("/findGroupCards")
    @ResponseBody
    @ApiOperation(value = "查询指定群中，不包含当前用户的所有名片（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "当前用户唯一标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "当前群ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "int", defaultValue = "600")
    })
    public AjaxResultDTO findGroupCards(String openId, String groupId, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            //消除红点提示
            userGroupService.removeHint(groupId, openId);
            PageDTO pageDTO = userGroupService.findCardsOnGroupByOpenId(openId, groupId, pageNum, pageSize);
            return AjaxResultDTO.success(pageDTO);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询群名片列表异常】：{}", e);
            return AjaxResultDTO.failed("查询群名片列表异常");
        }
    }

    @GetMapping("/findUserGroupByParam")
    @ResponseBody
    @ApiOperation(value = "根据用户传入的参数查询指定信息")
    public AjaxResultDTO findUserGroupByParam(
            @ApiParam(name = "用户传递的对象", value = "传入的JSON值", required = true) UserGroup userGroup,
            HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<ReturnGroupDTO> returnGroupDTOS = userGroupService.findUserGroupByParam(userGroup);
            return AjaxResultDTO.success(returnGroupDTOS);
        } catch(PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch(Exception e) {
            logger.error("【查询群列表异常】：{}", e);
            return AjaxResultDTO.failed("查询群列表异常");
        }
    }

    @GetMapping("/findAllGroupCardByParam")
    @ResponseBody
    @ApiOperation(value = "群内模糊搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "当前群组ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "openId", value = "当前用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "param", value = "搜索参数", required = false, dataType = "String")
    })
    public AjaxResultDTO findAllGroupCardByParam(String groupId, String openId, String param, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            List<ReturnCardDTO> returnCardDTOS = userGroupService.findAllGroupCardByParam(groupId, openId, param);
            return AjaxResultDTO.success(returnCardDTOS);
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【参数群查询名片异常】：{}", e);
            return AjaxResultDTO.failed("参数群查询名片异常");
        }
    }
}