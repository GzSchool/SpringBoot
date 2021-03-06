package com.eqxuan.peers.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 自定义前端返回格式
 */
@Getter
@Setter
@ToString
public class AjaxResultDTO {

    /** 请求状态.*/
    private Boolean success;

    /** 返回数据.*/
    private Object data;

    /** 错误信息.*/
    private String message;

    public AjaxResultDTO(boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static AjaxResultDTO success() {
        return new AjaxResultDTO(true, null, null);
    }

    public static AjaxResultDTO success(Object data) {
        return new AjaxResultDTO(true, data, null);
    }

    public static AjaxResultDTO failed(String message) {
        return new AjaxResultDTO(false, null, message);
    }

}
