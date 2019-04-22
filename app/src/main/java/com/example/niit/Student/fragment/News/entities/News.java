package com.example.niit.Student.fragment.News.entities;

import java.util.List;

public class News {


    private String idNews;

    private String Avatar_Username;
    private String userName;
    private String date;
    private String content;
    private int status;
    private int userLike;

    public String getIdNews() {
        return idNews;
    }

    public void setIdNews(String idNews) {
        this.idNews = idNews;
    }

    public int getUserLike() {
        return userLike;
    }

    public void setUserLike(int userLike) {
        this.userLike = userLike;
    }

    private List<UserComment> userComments;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<UserComment> getUserComments() {
        return userComments;
    }

    public void setUserComments(List<UserComment> userComments) {
        this.userComments = userComments;
    }

    class UserComment {
        private String avatar;
        private String usenName;
        private String contentComment;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUsenName() {
            return usenName;
        }

        public void setUsenName(String usenName) {
            this.usenName = usenName;
        }

        public String getContentComment() {
            return contentComment;
        }

        public void setContentComment(String contentComment) {
            this.contentComment = contentComment;
        }
    }
}
