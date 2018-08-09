package com.eqxuan.peers.exception;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 项目自定义异常
 */
public class PeerProjectException extends RuntimeException{

    public PeerProjectException() {

    }

    public PeerProjectException(String msg) {
        super(msg);
    }
}
