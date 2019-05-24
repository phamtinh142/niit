package com.example.niit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateAccountStudent {

    @SerializedName("password")
    @Expose
    private String password;

    public CreateAccountStudent(String password) {
        this.password = password;
    }

    public CreateAccountStudent() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
