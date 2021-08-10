package com.example.rpcfxprovider.service.impl;


import com.example.rpcfxapi.domain.User;
import com.example.rpcfxapi.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "YY" + System.currentTimeMillis());
    }
}
