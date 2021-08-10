package com.example.rpcfxcore.exception;

public class RpcfxException extends RuntimeException{

    private int status;

    private String msg;

    public RpcfxException(String message, Throwable cause, int status, String msg) {
        super(message, cause);
        this.status = status;
        this.msg = msg;
    }
}
