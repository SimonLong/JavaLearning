package com.example.rpcfxcore.api;

public interface RpcfxResolver {

    <T> T resolve(Class serviceClass);

}
