package com.example.sportsnews.model;


import org.litepal.crud.LitePalSupport;

/**
 * 用户实体类(属性和数据库中的字段一一对应)
 */

public class User extends LitePalSupport {
    private String name=null;       // 姓名(用户名)
    private String gender=null;     // 性别
    private String mail=null;       // 邮箱
    private String location=null;   // 城市
    private String motto=null;      // 个性签名
    private String password=null;   // 密码

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", mail='" + mail + '\'' +
                ", location='" + location + '\'' +
                ", motto='" + motto + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
