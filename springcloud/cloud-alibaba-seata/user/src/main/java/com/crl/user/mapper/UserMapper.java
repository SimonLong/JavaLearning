package com.crl.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crl.userdto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
