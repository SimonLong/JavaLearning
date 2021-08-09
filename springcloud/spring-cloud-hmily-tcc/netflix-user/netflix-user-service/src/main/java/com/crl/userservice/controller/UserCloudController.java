package com.crl.userservice.controller;

import com.crl.dto.User;
import com.crl.service.IUserServiceApi;
import com.crl.userservice.mapper.UserMapper;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCloudController implements IUserServiceApi {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Hmily(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    @Transactional //这里加这个如果回滚了的话，是否会有影响
    public String changeMoney(@RequestBody User user) {
        System.out.println("---------changMoney now---------");
        userMapper.changeMoney(user);
        return "success";
    }

    public void confirmMethod(User user) {
//        userMapper.changeMoney(user);
        System.out.println("--------confirm change money-----");
        System.out.println(user);
    }

    public void cancelMethod(User user) {
        User cancel = new User();
        cancel.setId(user.getId());
        cancel.setMoney(-user.getMoney());
        userMapper.changeMoney(cancel);
        System.out.println("---------cancel change money--------");
        System.out.println(user);
    }


}
