package com.codecademy.informationhandling.registration;

public class Registration {

    private int registerID;
    private String StudentEmail;
    private String registerDate;
    private String courseName;

    public Registration(int registerID, String studentEmail, String registerDate, String courseName) {
        this.registerID = registerID;
        StudentEmail = studentEmail;
        this.registerDate = registerDate;
        this.courseName = courseName;
    }

    public int getRegisterID() {
        return registerID;
    }

    public void setRegisterID(int registerID) {
        this.registerID = registerID;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRegistrationDate() {
        return"";
    }
}



