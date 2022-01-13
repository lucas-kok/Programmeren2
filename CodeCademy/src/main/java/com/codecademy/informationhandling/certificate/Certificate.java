package com.codecademy.informationhandling.certificate;

public class Certificate {

    private int CertificateID;
    private int RegisterID;
    private int score;
    private String StaffName;

    public Certificate(int certificateID, int registerID, int score, String staffName) {
        CertificateID = certificateID;
        RegisterID = registerID;
        this.score = score;
        StaffName = staffName;
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
