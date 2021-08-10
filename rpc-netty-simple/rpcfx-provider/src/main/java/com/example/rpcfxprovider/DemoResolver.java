package com.example.rpcfxprovider;

import com.example.rpcfxcore.api.RpcfxResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DemoResolver implements RpcfxResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T resolve(Class serviceClass) {
        return (T) this.applicationContext.getBean(serviceClass);
//        return this.applicationContext.getBean(serviceClass);
    }
}
