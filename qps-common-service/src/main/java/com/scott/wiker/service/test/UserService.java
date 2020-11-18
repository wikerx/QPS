package com.scott.wiker.service.test;

import com.scott.wiker.entity.test.Users;

import java.util.List;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :UserService
 * @description :
 * @data :2020/9/7 0007 上午 10:48
 * @status : 编写
 **/
public interface UserService {

    public List<Users> selectListUsers();

    public List<Users> selectListUsersMaster();

    public List<Users> selectListUsersSlave();
}
