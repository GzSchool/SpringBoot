package com.eqxuan.peers.enums;

import com.eqxuan.peers.exception.PeerProjectException;
import lombok.Getter;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 同行名片保存来源
 */
@Getter
public enum PeerShareFlagEnum {
    /**
     * 1:来源于个人分享
     */
    FLAG_BY_PERSON(1, "个人分享"),
    /**
     * 2:来源于群分享
     */
    FLAG_BY_GROUP(2, "群分享");

    private int key;

    private String value;

    private PeerShareFlagEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static PeerShareFlagEnum getPeerShareFlagEnum(int key) {
        for (PeerShareFlagEnum psfe : PeerShareFlagEnum.values()) {
            if (psfe.getKey() == key) {
                return psfe;
            }
        }
        throw new PeerProjectException("没有合适的状态");
    }
}
