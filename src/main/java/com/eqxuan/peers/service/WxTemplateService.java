package com.eqxuan.peers.service;

import net.sf.json.JSONObject;

public interface WxTemplateService {

    /**
     *用户创建名片后消息通知
     * @param openId
     * @param formId
     * @param makeTime
     * @return
     */
    JSONObject makeCardSuccess(String openId, String formId, String makeTime);

    /**
     * 保存同行信息后消息推送模板
     * @param openId
     * @param saveName
     * @param formId
     * @return
     */
    JSONObject saveCardSuccess(String openId, String saveName, String formId);
}
