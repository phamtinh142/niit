package com.example.niit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Manager {
    @SerializedName("type_account")
    @Expose
    private int type_account;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("avata")
    @Expose
    private String avata;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;

    public Manager(int type_account, String address, String avata, String email, String id, String name, String phone) {
        this.type_account = type_account;
        this.address = address;
        this.avata = avata;
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Manager() {

    }

    public int getType_account() {
        return type_account;
    }

    public void setType_account(int type_account) {
        this.type_account = type_account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
