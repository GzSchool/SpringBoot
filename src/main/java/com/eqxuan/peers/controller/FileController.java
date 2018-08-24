package com.eqxuan.peers.controller;

import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.WxQrCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: zheng guangjing.
 * @date: 2018/8/22 15:04
 * @description: 图片相关接口
 */
@RestController
@RequestMapping("/file")
@Api(tags = "图片相关接口 @郑光景", description = "FileController")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private WxQrCodeService wxQrCodeService;

    @GetMapping("/makeWxQrCode")
    @ResponseBody
    @ApiOperation(value = "用户个性化小程序码生成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPhotoUrl", value = "用户头像", required = true, dataType = "String"),
            @ApiImplicitParam(name = "scene", value = "小程序码参数", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "跳转页面（小程序需发布）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "openId", value = "用户标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cardId", value = "名片ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "index", value = "图片别名", required = true, dataType = "String")
    })
    public AjaxResultDTO makeWxQrCode(String userPhotoUrl, String scene, String page, String openId,String cardId, String index, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            String returnUrl = wxQrCodeService.makeWxQrCode(userPhotoUrl, scene, page, openId, cardId, index);
            return AjaxResultDTO.success(returnUrl);
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【生成小程序码异常】：{}", e);
            return AjaxResultDTO.failed("生成小程序码异常");
        }
    }

    @PostMapping("/fileUpload")
    @ResponseBody
    @ApiOperation(value = "上传图片")
    public List<String> fileUpload(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam(value = "file", required = false) MultipartFile[] multipartFiles) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            String openId = request.getParameter("openId");
            String cardId = request.getParameter("cardId");
            String index = request.getParameter("index");
            List<String> photoUrlVOS = wxQrCodeService.fileUpload(openId, cardId, multipartFiles, index);
            return photoUrlVOS;
        } catch (Exception e) {
            logger.error("【上传图片异常】：{}", e);
            return null;
        }
    }

    @GetMapping("/delFile")
    @ResponseBody
    @ApiOperation(value = "删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delFileUrl", value = "文件地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "openId", value = "用户标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cardId", value = "名片ID", required = true, dataType = "String")
    })
    public AjaxResultDTO delFile(String delFileUrl, String openId, String cardId, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            wxQrCodeService.delFile(delFileUrl, openId, cardId);
            return AjaxResultDTO.success();
        } catch (PeerProjectException ppe) {
            return AjaxResultDTO.failed(ppe.getMessage());
        } catch (Exception e) {
            logger.error("【删除文件异常】：{}", e);
            return AjaxResultDTO.failed("删除文件异常");
        }
    }
}