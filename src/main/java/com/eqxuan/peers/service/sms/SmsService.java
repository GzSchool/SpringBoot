package com.eqxuan.peers.service.sms;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;

/**
 * @author: Mature
 * @Description: 短信服务类
 * @date 2018/8/20 16:20
 */
@Service
public class SmsService {

	@Autowired
	private SmsSingleSender singleSender;

	@Autowired
	private SmsMultiSender multiSender;
	
	/**
	 * 自定义短信内容发送
	 * @param msg 短信内容
	 * @param mobile 用户手机号
	 * @return OK 成功  null 失败
	 */
	public String sendMessage(String mobile, String msg){
		try {
			SmsSingleSenderResult result = singleSender.send(0, "86", mobile, msg, "", "");
			return result.errMsg;
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送模板短信
	 * @param mobile 用户手机号
	 * @param templateId 模板Id
	 * @param params 模板参数
	 * @return OK 成功  null 失败
	 */
	public String sendTemplateMessage(String mobile, int templateId, String[] params){
		try {
			// 签名参数未提供或者为空时，会使用默认签名发送短信
			SmsSingleSenderResult result = singleSender.sendWithParam("86", mobile,
					templateId, params, "", "", "");
			return result.errMsg;
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 群发自定义短信
	 * @param msg 短信内容
	 * @param mobiles 用户手机号数组
	 * @return OK 成功 null 失败
	 */
	public String sendMultiMessage(String msg, String[] mobiles){
		try {
			SmsMultiSenderResult result =  multiSender.send(0, "86", mobiles, msg, "", "");
			return result.errMsg;
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 群发模板短信
	 * @param mobiles 用户手机号数组
	 * @param templateId 模板Id
	 * @param params 模板参数
	 * @return OK 成功 null 失败
	 */
	public String sendMultiTempleteMessage(String[] mobiles, int templateId, String[] params){
		try {
			SmsMultiSenderResult result =  multiSender.sendWithParam("86", mobiles,
					templateId, params, "", "", "");
			System.out.print(result);
			return result.errMsg;
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
