package com.zimuka.peers.vo;

public class UserPeerSaveVO {

    private String openId;

    private Integer peerCardId;

    private Integer saveFlag;

    private Integer delFlag;

    private String prepare;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getPeerCardId() {
        return peerCardId;
    }

    public Integer getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Integer saveFlag) {
        this.saveFlag = saveFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public void setPeerCardId(Integer peerCardId) {
        this.peerCardId = peerCardId;
    }

    public String getPrepare() {
        return prepare;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }
}
