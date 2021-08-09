package com.crl.orderservice.config;

import com.crl.dto.User;
import com.crl.service.IUserServiceApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign服务降级
 * 注：利用继承方式实现服务降级必须继承FallbackFactory，不能直接继承RefactorService;
 */
@Component
public class UserServiceFallback implements FallbackFactory<IUserServiceApi> {
    @Override
    public IUserServiceApi create(Throwable throwable) {
        return new IUserServiceApi() {
            @Override
            public String changeMoney(@RequestBody User user) {
                System.out.println(user.toString());
                throwable.printStackTrace();
                System.out.println("------------fallback by userService----------");
                return "fallback";
            }
        };
    }
}
