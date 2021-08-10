package com.example.rpcfxcore.client.proxy;

import com.example.rpcfxcore.client.AbstractRpcClient;
import com.example.rpcfxcore.client.RpcInvocationHandler;

import java.lang.reflect.Proxy;


public class RpcClientJdk extends AbstractRpcClient {

    @Override
    public <T> T create(Class<T> serviceClass, String url) {
        // 查询是否之前生成过，存储的直接返回
        if (!isExit(serviceClass.getName())) {
            add(serviceClass.getName(), newProxy(serviceClass, url));
        }
        return (T) getProxy(serviceClass.getName());
    }

    private <T> T newProxy(Class<T> serviceClass, String url) {
        ClassLoader loader = RpcClientJdk.class.getClassLoader();
        Class[] classes = new Class[]{serviceClass};
        return (T) Proxy.newProxyInstance(loader, classes, new RpcInvocationHandler(serviceClass, url));
    }
}
