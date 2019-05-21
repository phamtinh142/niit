package com.example.niit.Student.activity.CommentNews;

public class SendComment {
    private String id;
    private String content;
    private long createAtTime;
    private int type_account;
    private String classes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType_account() {
        return type_account;
    }

    public void setType_account(int type_account) {
        this.type_account = type_account;
    }

    public SendComment() {

    }

    public SendComment(String id, String content, long createAtTime, int type_account) {
        this.id = id;
        this.content = content;
        this.createAtTime = createAtTime;
        this.type_account = type_account;
    }

    public SendComment(String id, String content, long createAtTime, int type_account, String classes) {
        this.id = id;
        this.content = content;
        this.createAtTime = createAtTime;
        this.type_account = type_account;
        this.classes = classes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateAtTime() {
        return createAtTime;
    }

    public void setCreateAtTime(long createAtTime) {
        this.createAtTime = createAtTime;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
