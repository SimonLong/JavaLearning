package com.example.demo.mapper;

import com.example.demo.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {

    int insert(Users record);

    Users selectByPrimaryKey(Long id);

}
