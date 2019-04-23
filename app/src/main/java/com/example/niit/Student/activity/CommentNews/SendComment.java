package com.example.niit.Student.activity.CommentNews;

public class SendComment {
    private String avatar;
    private String username;
    private String content;
    private long createAtTime;

    public SendComment() {

    }

    public SendComment(String avatar, String username, String content, long createAtTime) {
        this.avatar = avatar;
        this.username = username;
        this.content = content;
        this.createAtTime = createAtTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
