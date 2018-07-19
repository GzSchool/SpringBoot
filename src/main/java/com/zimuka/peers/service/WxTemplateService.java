package com.zimuka.peers.service;

import net.sf.json.JSONObject;

public interface WxTemplateService {

    JSONObject messagePush(String formId, Integer userId, Integer peerCardId);
}
