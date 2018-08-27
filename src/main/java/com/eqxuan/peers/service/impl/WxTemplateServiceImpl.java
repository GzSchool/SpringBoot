package com.eqxuan.peers.service.impl;

import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.dto.TemplateData;
import com.eqxuan.peers.dto.WechatTemplate;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.mapper.UserCardMapper;
import com.eqxuan.peers.service.WxTemplateService;
import com.eqxuan.peers.utils.WxTemplateUtil;
import com.eqxuan.peers.vo.AccessToken;
import com.eqxuan.peers.config.MiniAppBean;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息模板推送
 */
@Service
public class WxTemplateServiceImpl implements WxTemplateService {


    @Autowired
    private MiniAppBean miniAppBean;

    @Resource
    private UserCardMapper userCardMapper;

    /**
     * 用户创建名片后消息通知
     * @param openId
     * @param formId
     * @param makeTime
     * @return
     */
    @Override
    public JSONObject makeCardSuccess(String openId, String formId, String makeTime) {

        WechatTemplate wechatTemplate = new WechatTemplate();
        //用户ID
        wechatTemplate.setTouser(openId);
        //表单ID
        wechatTemplate.setForm_id(formId);
        //模板ID
        wechatTemplate.setTemplate_id(miniAppBean.getMakeTemplateID());
        wechatTemplate.setPage("pages/findmore/findmore");

        Map<String, TemplateData> data = new HashMap<String, TemplateData>(2);
        data.put("keyword1", new TemplateData("您的电子名片已创建成功，点击查看", "#173177"));
        data.put("keyword2", new TemplateData(makeTime, "#173177"));
        wechatTemplate.setData(data);

        AccessToken accessToken = WxTemplateUtil.getAccessToken(miniAppBean.getAppId(), miniAppBean.getAppSecret());
        JSONObject jsonObject = WxTemplateUtil.sendTmplMsg(wechatTemplate, accessToken.getAccessToken());

        return jsonObject;
    }

    /**
     * 保存同行信息后消息推送模板
     * @param openId
     * @param cardId
     * @param saveName
     * @param formId
     * @return
     */
    @Override
    public JSONObject saveCardSuccess(String openId, String cardId, String saveName, String formId) {

        UserCard checkUserCard = userCardMapper.findById(Integer.parseInt(cardId));
        if (null == checkUserCard) {
            throw new PeerProjectException("用户未添加名片");
        }

        WechatTemplate wechatTemplate = new WechatTemplate();
        //用户ID
        wechatTemplate.setTouser(openId);
        //表单ID
        wechatTemplate.setForm_id(formId);
        //模板ID
        wechatTemplate.setTemplate_id(miniAppBean.getSaveTemplateID());
        wechatTemplate.setPage("pages/findmore/findmore");

        Map<String, TemplateData> data = new HashMap<String, TemplateData>(3);
        data.put("keyword1", new TemplateData(checkUserCard.getUsername(), "#173177"));
        data.put("keyword2", new TemplateData("点击进入小程序查看", "#173177"));
        data.put("keyword3", new TemplateData(saveName, "#173177"));
        wechatTemplate.setData(data);

        AccessToken accessToken = WxTemplateUtil.getAccessToken(miniAppBean.getAppId(), miniAppBean.getAppSecret());
        JSONObject jsonObject = WxTemplateUtil.sendTmplMsg(wechatTemplate, accessToken.getAccessToken());

        return jsonObject;
    }
}
