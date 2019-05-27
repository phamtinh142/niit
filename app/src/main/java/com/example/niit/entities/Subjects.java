package com.example.niit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subjects {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("numberSession")
    private String numberSession;

    private String keySubject;

    public Subjects(String id, String subject, String numberSession) {
        this.id = id;
        this.subject = subject;
        this.numberSession = numberSession;
    }

    public Subjects() {

    }

    public String getNumberSession() {
        return numberSession;
    }

    public void setNumberSession(String numberSession) {
        this.numberSession = numberSession;
    }

    public String getKeySubject() {
        return keySubject;
    }

    public void setKeySubject(String keySubject) {
        this.keySubject = keySubject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
