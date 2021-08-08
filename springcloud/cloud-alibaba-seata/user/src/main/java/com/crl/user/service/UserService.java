package com.crl.user.service;

import com.crl.user.mapper.UserMapper;
import com.crl.userdto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void changeMoney(User user) {
        User oldUser = userMapper.selectById(user.getId());
        oldUser.setMoney(oldUser.getMoney() + user.getMoney());
        oldUser.setMoneyFreeze(user.getMoneyFreeze());
        userMapper.updateById(user);
        if (user.getId().equals(1)) {
            throw new RuntimeException("模拟异常测试");
        }
    }
}
