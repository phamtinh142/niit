package com.example.niit.entities;

public class Score {
    private String idSubject;
    private String practicingScore;
    private String theoryScore;
    private String testAgain;

    public Score(String idSubject, String practicingScore, String theoryScore, String testAgain) {
        this.idSubject = idSubject;
        this.practicingScore = practicingScore;
        this.theoryScore = theoryScore;
        this.testAgain = testAgain;
    }

    public Score() {

    }

    public String getIdSubject() {
        return idSubject;
    }

    public String getTestAgain() {
        return testAgain;
    }

    public void setTestAgain(String testAgain) {
        this.testAgain = testAgain;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getPracticingScore() {
        return practicingScore;
    }

    public void setPracticingScore(String practicingScore) {
        this.practicingScore = practicingScore;
    }

    public String getTheoryScore() {
        return theoryScore;
    }

    public void setTheoryScore(String theoryScore) {
        this.theoryScore = theoryScore;
    }
}
