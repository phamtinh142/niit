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
    private String lastMessage;

    public CreateChatUID(List<memberUser> memberList, String lastMessage) {
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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
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
