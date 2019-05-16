package com.example.niit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    private String idNews;

    public String getIdNews() {
        return idNews;
    }

    public void setIdNews(String idNews) {
        this.idNews = idNews;
    }

    public News(String id, String content_news, String create_time, String image_news, int type_account, String classes) {
        this.id = id;
        this.content_news = content_news;
        this.create_time = create_time;
        this.image_news = image_news;
        this.type_account = type_account;
        this.classes = classes;
    }

    public News() {

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
}
