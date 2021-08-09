//package com.example.demo;
//
//import com.example.demo.entity.Users;
//import com.example.demo.service.UsersService;
//import org.junit.jupiter.api.Test;
////import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//@SpringBootTest
//class DemoApplicationTests {
//
//    @Autowired
//    private UsersService usersService;
//
//    @Test
//    public void insert() {
//        Users build = Users.builder().name("user").comment("ii").build();
//        usersService.insert(build);
//    }
//
//    @Test
//    public void save() {
//        Users build = Users.builder().name("user").comment("ii").build();
//        usersService.insert(build);
//    }
//
//    @Test
//    public void selectByPrimaryKey() {
//        Users users = usersService.selectByPrimaryKey(1L);
//        System.out.println(users);
//    }
//
//}
