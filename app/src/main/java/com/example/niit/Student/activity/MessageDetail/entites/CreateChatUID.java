package com.example.niit.Student.activity.MessageDetail.entites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateChatUID {
    @SerializedName("memberList")
    @Expose
    private List<memberUser> memberList;
    @SerializedName("lastMessage")
    @Expose
    private LastMessage lastMessage;

    private String chatID;

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public CreateChatUID(List<memberUser> memberList, LastMessage lastMessage) {
        this.memberList = memberList;
        this.lastMessage = lastMessage;
    }

    public CreateChatUID() {

    }

    public List<memberUser> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<memberUser> memberList) {
        this.memberList = memberList;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public static class LastMessage {
        private String sentBy;
        private long createTime;
        private String message;

        public LastMessage(String sentBy, long createTime, String message) {
            this.sentBy = sentBy;
            this.createTime = createTime;
            this.message = message;
        }

        public LastMessage() {

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

    public static class memberUser {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("typeAccount")
        @Expose
        private int typeAccount;
        @SerializedName("classes")
        private String classes;

        public memberUser(String id, int typeAccount, String classes) {
            this.id = id;
            this.typeAccount = typeAccount;
            this.classes = classes;
        }

        public memberUser(String id, int typeAccount) {
            this.id = id;
            this.typeAccount = typeAccount;
        }

        public memberUser() {

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

        public int getTypeAccount() {
            return typeAccount;
        }

        public void setTypeAccount(int typeAccount) {
            this.typeAccount = typeAccount;
        }
    }
}
