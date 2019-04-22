package com.example.niit.Student.InfoUser;

public class User {
    private String Address;
    private int Age;
    private String Email;
    private String Image;
    private String Name;
    private String Phone;
    private int Sex;

    public User() {
    }

    public User(String address, int age, String email, String image, String name, String phone, int sex) {
        Address = address;
        Age = age;
        Email = email;
        Image = image;
        Name = name;
        Phone = phone;
        Sex = sex;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }
}
