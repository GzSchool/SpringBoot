package com.zimuka.peers.service;

import net.sf.json.JSONObject;

public interface WxTemplateService {

    JSONObject makeCardSuccess(String openId, String formId, String makeTime);
}
