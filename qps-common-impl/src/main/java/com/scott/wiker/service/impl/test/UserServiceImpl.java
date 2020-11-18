package com.scott.wiker.service.impl.test;

import com.scott.wiker.dataSource.annotation.DataSource;
import com.scott.wiker.entity.test.Users;
import com.scott.wiker.mapper.test.UserMapper;
import com.scott.wiker.service.test.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :UserServiceImpl
 * @description :
 * @data :2020/9/7 0007 上午 10:49
 * @status : 编写
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @DataSource
    @Override
    public List<Users> selectListUsers() {
        return userMapper.selectListUsers();
    }


    @Override
    @DataSource
    public List<Users> selectListUsersMaster(){
        return userMapper.selectListUsers();
    }


    @Override
    @DataSource("slave1")
    public List<Users> selectListUsersSlave(){

        return userMapper.selectListUsers();
    }
}
