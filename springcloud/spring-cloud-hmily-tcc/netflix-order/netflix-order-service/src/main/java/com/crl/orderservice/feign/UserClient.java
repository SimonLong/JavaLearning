package com.crl.orderservice.feign;

import com.crl.orderservice.config.UserServiceFallback;
import com.crl.service.IUserServiceApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

//此方式不会经过网关，负载均衡由当前客户端负责
@FeignClient(name="user-service", fallbackFactory  = UserServiceFallback.class)
//@Service
public interface UserClient extends IUserServiceApi {

}
