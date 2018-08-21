package com.eqxuan.peers.controller;

import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.dto.AuthorizeDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.UserService;
import com.eqxuan.peers.service.WxQrCodeService;
import io.swagger.annotations.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: zheng guangjing.
 * @Date: 2018/8/13 16:05
 * @Description: 用户相关接口
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口 @郑光景", description = "UserController")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private WxQrCodeService wxQrCodeService;

    @GetMapping("/userAuthor")
    @ResponseBody
    @ApiOperation(value = "用户登录授权接口")
    @ApiImplicitParam(name = "code", value = "用户登陆码", required = true, dataType = "string")
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

    @GetMapping("/makeWxQrCode")
    @ResponseBody
    @ApiOperation(value = "用户个性化小程序码生成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPhotoUrl", value = "用户头像", required = true, dataType = "String"),
            @ApiImplicitParam(name = "scene", value = "小程序码参数", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "跳转页面（小程序需发布）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "openId", value = "用户标识", required = true, dataType = "String"),
    })
    public AjaxResultDTO makeWxQrCode(String userPhotoUrl, String scene, String page, String openId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            String pathName = wxQrCodeService.makeWxQrCode(userPhotoUrl, scene, page, openId);
            return AjaxResultDTO.success(pathName);
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【生成小程序码异常】：{}", e);
            return AjaxResultDTO.failed("生成小程序码异常");
        }
    }

    @GetMapping("/getUserPhone")
    @ResponseBody
    @ApiOperation(value = "获取用户绑定手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "iv", value = "偏移量", required = true, dataType = "String"),
            @ApiImplicitParam(name = "encryptedData", value = "加密信息", required = true, dataType = "String")
    })
    public AjaxResultDTO getUserPhone(String openId, String iv, String encryptedData, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            String phoneNumber = userService.getUserPhone(openId, iv, encryptedData);
            return AjaxResultDTO.success(phoneNumber);
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【解析用户信息异常】：{}", e);
            return AjaxResultDTO.failed("解析用户信息异常");
        }
    }

    @PostMapping("/fileUpload")
    @ResponseBody
    @ApiOperation(value = "上传图片")
    public AjaxResultDTO fileUpload(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam(value = "file", required = false) MultipartFile[] multipartFiles) {

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            String openId = request.getParameter("openId");
            String cardId = request.getParameter("cardId");
            String index = request.getParameter("index");
            String url = wxQrCodeService.fileUpload(openId, cardId, multipartFiles, index);
            return AjaxResultDTO.success(url);
        } catch (Exception e) {
            logger.error("【上传图片异常】：{}", e);
            return AjaxResultDTO.failed("上传图片异常");
        }
    }
}
