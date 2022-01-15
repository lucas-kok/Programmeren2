package com.codecademy.informationhandling.registration;

public class Registration {
    private final int id;
    private final String StudentEmail;
    private final String registerDate;
    private String courseName;

    public Registration(int registerID, String studentEmail, String registerDate, String courseName) {
        this.id = registerID;
        StudentEmail = studentEmail;
        this.registerDate = registerDate;
        this.courseName = courseName;
    }

    public int getRegisterID() {
        return id;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public String getRegistrationDate() {
        return registerDate;
    }

    public String getRegistrationDateAsString() {
        String[] registrationDatePieces = getRegistrationDatePieces();
        return registrationDatePieces[2] + "/" + registrationDatePieces[1] + "/" + registrationDatePieces[0];
    }

    public String[] getRegistrationDatePieces() { return getRegistrationDate().split("-"); }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}



