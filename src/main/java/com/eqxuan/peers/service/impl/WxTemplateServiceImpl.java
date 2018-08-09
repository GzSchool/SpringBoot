package com.eqxuan.peers.service.impl;

import com.eqxuan.peers.dto.TemplateData;
import com.eqxuan.peers.dto.WechatTemplate;
import com.eqxuan.peers.service.WxTemplateService;
import com.eqxuan.peers.utils.WxTemplateUtil;
import com.eqxuan.peers.vo.AccessToken;
import com.eqxuan.peers.configBeans.MiniAppBean;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息模板推送
 */
@Service
public class WxTemplateServiceImpl implements WxTemplateService {


    @Autowired
    private MiniAppBean miniAppBean;

    @Override
    public JSONObject makeCardSuccess(String openId, String formId, String makeTime) {

        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTouser(openId);//用户ID
        wechatTemplate.setForm_id(formId);//表单ID
        wechatTemplate.setTemplate_id("kW19kb8Tq0WdL-cFQq61pJjToO7fRL7Vs1MIk06c-cA");//模板ID
        wechatTemplate.setPage("pages/findmore/findmore");

        Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("keyword1", new TemplateData("您的电子名片已创建成功，点击查看", "#173177"));
        data.put("keyword2", new TemplateData(makeTime, "#173177"));
        wechatTemplate.setData(data);

        AccessToken accessToken = WxTemplateUtil.getAccessToken(miniAppBean.getAppId(), miniAppBean.getAppSecret());
        JSONObject jsonObject = WxTemplateUtil.sendTmplMsg(wechatTemplate, accessToken.getAccessToken());

        return jsonObject;
    }

    @Override
    public JSONObject saveCardSuccess(String openId, String saveName, String formId, String saveTime) {

        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTouser(openId);//用户ID
        wechatTemplate.setForm_id(formId);//表单ID
        wechatTemplate.setTemplate_id("Xl-42LMy09DsvtarIk5DFe6qLc_sxwQnQs5h5VnQ_qU");//模板ID
        wechatTemplate.setPage("pages/findmore/findmore");

        Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("keyword1", new TemplateData(saveName + "保存了您的电子名片", "#173177"));
        data.put("keyword2", new TemplateData("点击进入小程序查看", "#173177"));
        data.put("keyword3", new TemplateData(saveTime, "#173177"));
        wechatTemplate.setData(data);

        AccessToken accessToken = WxTemplateUtil.getAccessToken(miniAppBean.getAppId(), miniAppBean.getAppSecret());
        JSONObject jsonObject = WxTemplateUtil.sendTmplMsg(wechatTemplate, accessToken.getAccessToken());

        return jsonObject;
    }
}
