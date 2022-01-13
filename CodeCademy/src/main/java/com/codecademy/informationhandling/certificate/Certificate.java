package com.codecademy.informationhandling.certificate;

public class Certificate {

    private int CertificateID;
    private int RegisterID;
    private String StudentEmail;
    private String CourseName;
    private int score;
    private String StaffName;

    public Certificate(int certificateID, int registerID, String studentEmail, String courseName, int score, String staffName) {
        this.CertificateID = certificateID;
        this.RegisterID = registerID;
        this.score = score;
        this.StaffName = staffName;
        this.StudentEmail = studentEmail;
        this.CourseName = courseName;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public int getCertificateID() {
        return CertificateID;
    }

    public void setCertificateID(int certificateID) {
        CertificateID = certificateID;
    }

    public int getRegisterID() {
        return RegisterID;
    }

    public void setRegisterID(int registerID) {
        RegisterID = registerID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }
}
