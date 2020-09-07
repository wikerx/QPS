package com.scott.wiker.entity.test;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :Users
 * @description :
 * @data :2020/9/7 0007 上午 10:32
 * @status : 编写
 **/
public class Users {
    private Long id;
    private int age;
    private int sex;
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
