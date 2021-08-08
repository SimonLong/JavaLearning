package com.crl.user.controller;

import com.crl.user.service.UserService;
import com.crl.userdto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/changeMoney")
    public String changeMoney(@RequestBody User user) {
        userService.changeMoney(user);
        return "success";
    }

}
