package com.example.rpcfxcore.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcfxRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serviceClass;
    private String method;
    private Object[] params;
}
