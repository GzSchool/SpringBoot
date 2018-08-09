package com.eqxuan.peers.vo;

import java.util.List;

public class CreatePeersVO {

    private String openId;

    private List<Integer> cardIds;

    private Integer saveFlag;

    private String groupId;

    private String saveName;

    private String fromId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Integer> cardIds) {
        this.cardIds = cardIds;
    }

    public Integer getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Integer saveFlag) {
        this.saveFlag = saveFlag;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
}
