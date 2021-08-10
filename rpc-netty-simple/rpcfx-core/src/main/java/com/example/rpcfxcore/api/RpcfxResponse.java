package com.example.rpcfxcore.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcfxResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object result;
    private Boolean status;
    private Exception exception;
}
