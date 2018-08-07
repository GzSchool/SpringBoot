package com.zimuka.peers.dto;

import java.util.Date;
import java.util.List;

public class ReturnGroupDTO {

    private String groupId;

    private String openId;

    private Date ctTime;

    private Date upTime;

    private Integer saveFalse;

    private Integer saveTrue;

    private List<String> beforeNineImg;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCtTime() {
        return ctTime;
    }

    public void setCtTime(Date ctTime) {
        this.ctTime = ctTime;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Integer getSaveFalse() {
        return saveFalse;
    }

    public void setSaveFalse(Integer saveFalse) {
        this.saveFalse = saveFalse;
    }

    public Integer getSaveTrue() {
        return saveTrue;
    }

    public void setSaveTrue(Integer saveTrue) {
        this.saveTrue = saveTrue;
    }

    public List<String> getBeforeNineImg() {
        return beforeNineImg;
    }

    public void setBeforeNineImg(List<String> beforeNineImg) {
        this.beforeNineImg = beforeNineImg;
    }
}
