package com.eqxuan.peers.enums;

import com.eqxuan.peers.exception.PeerProjectException;
import lombok.Getter;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 是否本人分享名片到群
 */
@Getter
public enum GroupShareFlagEnum {

    /**
     * 本人分享
     */
    FLAG_BY_ME("1", "本人分享"),

    /**
     * 他人分享
     */
    FLAG_BY_OTHER("2", "他人分享");

    private String key;

    private String value;

    private GroupShareFlagEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static GroupShareFlagEnum getGroupShareFlagEnum(String key) {
        for (GroupShareFlagEnum gsfe : GroupShareFlagEnum.values()) {
            if (gsfe.getKey().equals(key)) {
                return gsfe;
            }
        }
        throw new PeerProjectException("没有合适的状态");
    }
}
