package com.eqxuan.peers.config;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsVoiceVerifyCodeSender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author: Mature
 * @Description: 短信配置
 * @date 2018/8/20 16:11
 */
@Component
@Configuration
@Getter
@Setter
public class SmsConfig {

	@Value("${SMS.AppID}")
	private int appId;

	@Value("${SMS.AppKey}")
	private String appKey;

	/**
	 * 签名
	 */
	@Value("${SMS.sign}")
	private String sign;

	/**
	 * 短信验证码模板
	 */
	@Value("${SMS.code.templateID}")
	private int codeTemplateID;

	@Bean(name = "singleSender")
	public SmsSingleSender singleSenderBean(){
		return new SmsSingleSender(appId, appKey);
	}

	@Bean(name = "multiSender")
	public SmsMultiSender multiSenderBean(){
		return new SmsMultiSender(appId, appKey);
	}

	@Bean(name = "voiceCodeSender")
	public SmsVoiceVerifyCodeSender voiceCodeSenderBean(){
		return new SmsVoiceVerifyCodeSender(appId, appKey);
	}

}
