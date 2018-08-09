package com.eqxuan.peers.enums;

import com.eqxuan.peers.exception.PeerProjectException;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 同行信息保存状态
 */
public enum PeerCardSaveFlagEnum {

    SAVE_FLAG_FALSE(1, "未保存"),
    SAVE_FLAG_TRUE(2, "保存");

    private int key;

    private String value;

    private PeerCardSaveFlagEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static PeerCardSaveFlagEnum getPeerCardSaveFlagEnum(int key) {
        for (PeerCardSaveFlagEnum pcsfe : PeerCardSaveFlagEnum.values()) {
            if (pcsfe.getKey() == key) {
                return pcsfe;
            }
        }
        throw new PeerProjectException("没有合适的状态");
    }
}
