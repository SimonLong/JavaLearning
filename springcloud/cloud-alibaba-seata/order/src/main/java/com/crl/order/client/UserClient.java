package com.crl.order.client;


import com.crl.userdto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping("/user/changeMoney")
    String changeMoney(@RequestBody User user);
}
