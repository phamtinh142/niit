package com.example.niit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedSchelude {
    @SerializedName("thu2")
    @Expose
    private String thu2;
    @SerializedName("thu3")
    @Expose
    private String thu3;
    @SerializedName("thu4")
    @Expose
    private String thu4;
    @SerializedName("thu5")
    @Expose
    private String thu5;
    @SerializedName("thu6")
    @Expose
    private String thu6;
    @SerializedName("thu7")
    @Expose
    private String thu7;

    public CreatedSchelude(String thu2, String thu3, String thu4, String thu5, String thu6, String thu7) {
        this.thu2 = thu2;
        this.thu3 = thu3;
        this.thu4 = thu4;
        this.thu5 = thu5;
        this.thu6 = thu6;
        this.thu7 = thu7;
    }

    public CreatedSchelude() {

    }

    public String getThu2() {
        return thu2;
    }

    public void setThu2(String thu2) {
        this.thu2 = thu2;
    }

    public String getThu3() {
        return thu3;
    }

    public void setThu3(String thu3) {
        this.thu3 = thu3;
    }

    public String getThu4() {
        return thu4;
    }

    public void setThu4(String thu4) {
        this.thu4 = thu4;
    }

    public String getThu5() {
        return thu5;
    }

    public void setThu5(String thu5) {
        this.thu5 = thu5;
    }

    public String getThu6() {
        return thu6;
    }

    public void setThu6(String thu6) {
        this.thu6 = thu6;
    }

    public String getThu7() {
        return thu7;
    }

    public void setThu7(String thu7) {
        this.thu7 = thu7;
    }
}
