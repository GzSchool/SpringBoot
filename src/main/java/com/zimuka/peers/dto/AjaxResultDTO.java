package com.zimuka.peers.dto;

/**
 * 自定义前端返回格式
 */
public class AjaxResultDTO {

    /* 请求结果 */
    private boolean success;

    /* 成功后返回数据 */
    private Object data;

    /* 错误信息 */
    private String message;

    public AjaxResultDTO() {

    }

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static AjaxResultDTO failed(String message) {
        return new AjaxResultDTO(false, null, message);
    }

}
