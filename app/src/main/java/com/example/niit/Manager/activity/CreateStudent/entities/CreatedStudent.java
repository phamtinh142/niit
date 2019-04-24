package com.example.niit.Manager.activity.CreateStudent.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedStudent {
    @SerializedName("password")
    @Expose
    private String address;
    @SerializedName("password")
    @Expose
    private String age;
    @SerializedName("password")
    @Expose
    private String classUser;
    @SerializedName("password")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String avatar;
    @SerializedName("password")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String sex;
    @SerializedName("password")
    @Expose
    private String bithday;

    public CreatedStudent() {

    }

    public CreatedStudent(String address, String age, String classUser, String email, String avatar, String name, String phone, String sex, String bithday) {
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
