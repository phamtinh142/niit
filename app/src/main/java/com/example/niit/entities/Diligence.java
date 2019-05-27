package com.example.niit.entities;

public class Diligence {
    String idSubject;
    String numberSession;
    String numberHoliday;

    public Diligence(String idSubject, String numberSession, String numberHoliday) {
        this.idSubject = idSubject;
        this.numberSession = numberSession;
        this.numberHoliday = numberHoliday;
    }

    public Diligence() {

    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getNumberSession() {
        return numberSession;
    }

    public void setNumberSession(String numberSession) {
        this.numberSession = numberSession;
    }

    public String getNumberHoliday() {
        return numberHoliday;
    }

    public void setNumberHoliday(String numberHoliday) {
        this.numberHoliday = numberHoliday;
    }
}
