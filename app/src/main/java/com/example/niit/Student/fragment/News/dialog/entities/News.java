package com.example.niit.Student.fragment.News.dialog.entities;

public class News {
    private String userName;
    private String avatarUsername;
    private String id;
    private String createTime;
    private String imageNews;
    private String contentNews;

    public News(String userName, String avatarUsername, String id, String createTime, String imageNews, String contentNews) {
        this.userName = userName;
        this.avatarUsername = avatarUsername;
        this.id = id;
        this.createTime = createTime;
        this.imageNews = imageNews;
        this.contentNews = contentNews;
    }

    public News() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUsername() {
        return avatarUsername;
    }

    public void setAvatarUsername(String avatarUsername) {
        this.avatarUsername = avatarUsername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImageNews() {
        return imageNews;
    }

    public void setImageNews(String imageNews) {
        this.imageNews = imageNews;
    }

    public String getContentNews() {
        return contentNews;
    }

    public void setContentNews(String contentNews) {
        this.contentNews = contentNews;
    }
}
