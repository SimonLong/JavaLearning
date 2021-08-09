package com.crl.userservice.mapper;

import com.crl.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void changeMoney(User user);
}
