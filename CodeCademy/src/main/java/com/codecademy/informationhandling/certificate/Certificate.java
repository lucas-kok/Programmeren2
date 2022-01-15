package com.codecademy.informationhandling.certificate;

public class Certificate {
    private final int certificateID;
    private final int registrationID;
    private final String studentEmail;
    private String courseName;
    private int score;
    private String staffName;

    public Certificate(int certificateID, int registerID, String studentEmail, String courseName, int score, String staffName) {
        this.certificateID = certificateID;
        this.registrationID = registerID;
        this.score = score;
        this.staffName = staffName;
        this.studentEmail = studentEmail;
        this.courseName = courseName;
    }

    public int getCertificateID() {
        return certificateID;
    }

    public int getRegisterID() {
        return registrationID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
