package com.example.niit.entities;

public class Chats {
    private String sentBy;
    private long createTime;
    private String message;
    private String classes;
    private int typeAccount;

    public int getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(int typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Chats(int typeAccount, String sentBy, long createTime, String message, String classes) {
        this.typeAccount = typeAccount;
        this.sentBy = sentBy;
        this.createTime = createTime;
        this.message = message;
        this.classes = classes;
    }

    public Chats(int typeAccount, String sentBy, long createTime, String message) {
        this.typeAccount = typeAccount;
        this.sentBy = sentBy;
        this.createTime = createTime;
        this.message = message;
    }

    public Chats() {

    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
