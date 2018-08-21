package com.eqxuan.peers.controller;

import com.eqxuan.peers.config.SmsConfig;
import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.AjaxResultDTO;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.cache.impl.RedisService;
import com.eqxuan.peers.service.sms.SmsService;
import com.eqxuan.peers.utils.RandomUtil;
import com.github.qcloudsms.SmsSingleSenderResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Mature
 * @Description: 短信接口
 * @date 2018/8/20 16:18
 */
@RestController
@RequestMapping("/sms")
@Api(tags = "短信相关接口 @马晓亮", description = "SmsController")
public class SmsController {

	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

	/**
	 * 验证码长度
	 */
	private static int CODE_LEN = 4;

	/**
	 * 验证码缓存前缀
	 */
	private static String CACHE_CODE_PREFIX = "SMS_CODE_";

	@Autowired
	private SmsService smsService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private  SmsConfig smsConfig;

	@GetMapping("/sendCode")
	@ApiOperation(value = "发送短信验证码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "openId", value = "当前用户唯一标识", required = true, dataType = "String"),
			@ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String")
	})
	public AjaxResultDTO sendCode(String openId, String mobile, HttpServletResponse response){
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			String smsCode = RandomUtil.getNumberString(CODE_LEN);
			String result = smsService.sendTemplateMessage(mobile, smsConfig.getCodeTemplateID(), new String[]{smsCode});
			if ("OK".equals(result)){
				redisService.set(CACHE_CODE_PREFIX + openId, smsCode+":"+mobile, 300L);
				return AjaxResultDTO.success();
			}else {
				logger.error("【发送短信验证码异常】:{}", result);
				return AjaxResultDTO.failed("发送短信验证码异常");
			}
		} catch(PeerProjectException ppe) {
			return AjaxResultDTO.failed(ppe.getMessage());
		} catch(Exception e) {
			logger.error("【发送短信验证码异常】:{}", e);
			return AjaxResultDTO.failed("发送短信验证码异常");
		}
	}

	@GetMapping("/checkCode")
	@ApiOperation(value = "校验验证码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "openId", value = "当前用户唯一标识", required = true, dataType = "String"),
			@ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String"),
			@ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String")
	})
	public AjaxResultDTO checkCode(String openId, String mobile, String code, HttpServletResponse response){
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			boolean isExist = redisService.exists(CACHE_CODE_PREFIX + openId);
			if(!isExist){
				return AjaxResultDTO.failed("验证码已过期，请重新获取。");
			}else {
				String result = (String) redisService.get(CACHE_CODE_PREFIX + openId);
				String getCode = result.substring(0,CODE_LEN);
				String getMobile = result.substring(CODE_LEN+1, result.length());
				if (!getMobile.equals(mobile)){
					return AjaxResultDTO.failed("校验验证码失败，手机号变更！");
				}
				if (!getCode.equals(code)){
					return AjaxResultDTO.failed("校验验证码失败，验证码不正确！");
				}
				return AjaxResultDTO.success();
			}
		} catch(PeerProjectException ppe) {
			return AjaxResultDTO.failed(ppe.getMessage());
		} catch(Exception e) {
			logger.error("【校验验证码异常】:{}", e);
			return AjaxResultDTO.failed("校验验证码异常");
		}
	}

}
