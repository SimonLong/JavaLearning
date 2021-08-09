package com.crl.service;

import com.crl.dto.User;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/user")
public interface IUserServiceApi {

    @PostMapping(value = "/changeMoney")
    @Hmily
    String changeMoney(@RequestBody User user);
}
