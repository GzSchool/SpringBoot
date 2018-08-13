package com.eqxuan.peers.enums;

import com.eqxuan.peers.exception.PeerProjectException;
import lombok.Getter;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 同行信息保存状态
 */
@Getter
public enum PeerCardSaveFlagEnum {

    /**
     * 1:未保存的同行信息
     */
    SAVE_FLAG_FALSE(1, "未保存"),

    /**
     * 2:已保存的同行信息
     */
    SAVE_FLAG_TRUE(2, "保存");

    private int key;

    private String value;

    private PeerCardSaveFlagEnum(int key, String value) {
        this.key = key;
        this.value = value;
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
