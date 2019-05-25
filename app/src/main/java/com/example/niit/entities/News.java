package com.example.niit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("content_news")
    @Expose
    private String content_news;
    @SerializedName("create_time")
    @Expose
    private String create_time;
    @SerializedName("image_news")
    @Expose
    private String image_news;
    @SerializedName("type_account")
    @Expose
    private int type_account;
    @SerializedName("classes")
    private String classes;
    @SerializedName("classUser")
    private String classUser;
    @SerializedName("like")
    @Expose
    private List<String> likeList;

    private int countLike;
    private int countComment;
    private String idNews;

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public int getCountComment() {
        return countComment;
    }

    public void setCountComment(int countComment) {
        this.countComment = countComment;
    }

    public String getIdNews() {
        return idNews;
    }

    public void setIdNews(String idNews) {
        this.idNews = idNews;
    }

    public News(String id, String content_news, String create_time, String image_news, int type_account, String classes, List<String> likeList) {
        this.id = id;
        this.content_news = content_news;
        this.create_time = create_time;
        this.image_news = image_news;
        this.type_account = type_account;
        this.classes = classes;
        this.likeList = likeList;
    }

    public News(String id, String content_news, String create_time, String image_news, int type_account, String classes, String classUser, List<String> likeList) {
        this.id = id;
        this.content_news = content_news;
        this.create_time = create_time;
        this.image_news = image_news;
        this.type_account = type_account;
        this.classes = classes;
        this.classUser = classUser;
        this.likeList = likeList;
    }

    public News() {

    }

    public List<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<String> likeList) {
        this.likeList = likeList;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_news() {
        return content_news;
    }

    public void setContent_news(String content_news) {
        this.content_news = content_news;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getImage_news() {
        return image_news;
    }

    public void setImage_news(String image_news) {
        this.image_news = image_news;
    }

    public int getType_account() {
        return type_account;
    }

    public void setType_account(int type_account) {
        this.type_account = type_account;
    }

    public String getClassUser() {
        return classUser;
    }

    public void setClassUser(String classUser) {
        this.classUser = classUser;
    }
}
