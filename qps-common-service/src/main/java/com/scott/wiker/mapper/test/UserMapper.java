package com.scott.wiker.mapper.test;

import com.scott.wiker.entity.test.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :UserMapper
 * @description :
 * @data :2020/9/7 0007 上午 10:34
 * @status : 编写
 **/
@Mapper
public interface UserMapper {
    @Select("select * from user")
    public List<Users> selectListUsers();
}
