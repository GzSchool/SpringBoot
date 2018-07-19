package com.zimuka.peers.service.impl;

import com.zimuka.peers.dto.TemplateData;
import com.zimuka.peers.dto.WechatTemplate;
import com.zimuka.peers.service.WxTemplateService;
import com.zimuka.peers.utils.WxTemplateUtil;
import com.zimuka.peers.vo.AccessToken;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class WxTemplateServiceImpl implements WxTemplateService {

    //TODO  消息模板
    @Override
    public JSONObject messagePush(String formId, Integer userId, Integer peerCardId) {

        //封装消息模板内容
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id("");//模板ID
        wechatTemplate.setForm_id(formId);//表单ID

        wechatTemplate.setTouser("openId");//openId,根据peerCardId去user表中查询

        Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("keyword1", new TemplateData("消息模板推送","#173177"));
        wechatTemplate.setData(data);

        String AppId = "";
        String AppSecret = "";

        AccessToken accessToken = WxTemplateUtil.getAccessToken(AppId, AppSecret);

        JSONObject jsonObject = WxTemplateUtil.sendTmplMsg(wechatTemplate, accessToken.getAccessToken());

        return jsonObject;
    }
}
