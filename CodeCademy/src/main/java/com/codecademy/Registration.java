package com.codecademy;

public class Registration {
    private String registrationDate;
    private String studentEmail;
    private String courseName;

    public Registration(String registrationDate, String studentEmail, String courseName) {
        this.registrationDate = registrationDate;
        this.studentEmail = studentEmail;
        this.courseName = courseName;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String toString() {
        return getRegistrationDate() + ": " + getStudentEmail() + " - " + getCourseName();
    }
}
