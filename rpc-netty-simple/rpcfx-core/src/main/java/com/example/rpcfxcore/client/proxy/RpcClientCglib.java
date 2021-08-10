package com.example.rpcfxcore.client.proxy;

import com.example.rpcfxcore.client.AbstractRpcClient;
import com.example.rpcfxcore.client.RpcInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;


@Slf4j
public class RpcClientCglib extends AbstractRpcClient {

    @Override
    public <T> T create(Class<T> serviceClass, String url) {
        if (!isExit(serviceClass.getName())) {
            add(serviceClass.getName(), newProxy(serviceClass, url));
        }
        return (T) getProxy(serviceClass.getName());
    }

    private <T> T newProxy(Class<T> serviceClass, String url) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new RpcInvocationHandler(serviceClass, url));
        enhancer.setSuperclass(serviceClass);
        log.info("client cglib proxy instance create and return");
        return (T) enhancer.create();
    }
}
