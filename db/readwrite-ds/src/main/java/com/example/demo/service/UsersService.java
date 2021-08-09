package com.example.demo.service;

import com.example.demo.anno.Master;
import com.example.demo.entity.Users;
import com.example.demo.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersMapper usersMapper;

    public int insert(Users aaa) {
        return usersMapper.insert(aaa);
    }

    @Master
    public int save(Users aaa) {
        return usersMapper.insert(aaa);
    }

    public Users selectByPrimaryKey(Long id) {
        return usersMapper.selectByPrimaryKey(id);
    }

    @Master
    public Users getById(Long id) {
        //  有些读操作必须读主数据库
        //  比如，获取微信access_token，因为高峰时期主从同步可能延迟
        //  这种情况下就必须强制从主数据读
        return usersMapper.selectByPrimaryKey(id);
    }
}
