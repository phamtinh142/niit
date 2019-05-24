package com.example.niit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedStudent {
    public int getType_account() {
        return type_account;
    }

    public void setType_account(int type_account) {
        this.type_account = type_account;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("classUser")
    @Expose
    private String classUser;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("bithday")
    @Expose
    private String bithday;
    @SerializedName("type_account")
    @Expose
    private int type_account;

    public CreatedStudent() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CreatedStudent(int type_account, String id, String address, String age, String classUser, String email, String avatar, String name, String phone, String sex, String bithday) {
        this.type_account = type_account;
        this.id = id;
        this.address = address;
        this.age = age;
        this.classUser = classUser;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.bithday = bithday;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getClassUser() {
        return classUser;
    }

    public void setClassUser(String classUser) {
        this.classUser = classUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBithday() {
        return bithday;
    }

    public void setBithday(String bithday) {
        this.bithday = bithday;
    }
}
