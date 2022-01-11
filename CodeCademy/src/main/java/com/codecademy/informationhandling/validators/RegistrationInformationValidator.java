package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.Course.Course;
import com.codecademy.informationhandling.Student.Student;

import java.util.Map;

public class RegistrationInformationValidator {

    public RegistrationInformationValidator() {

    }

    public String validateNewRegistration(String[] registrationDatePieces, String studentEmail, String courseName, Map<String, Student> students, Map<String, Course> courses) {
        StringBuilder message = new StringBuilder();

        if (!validateRegistrationDate(registrationDatePieces)) message.append("The registration date is not valid!");
        if (!validateStudentEmail(studentEmail, students)) message.append("The email: '").append(studentEmail).append("' does not exists!");
        if (!validateCourseName(courseName, courses)) message.append("The Course: '").append(courseName).append("' does not exists!");

        return message.toString();
    }

    // Registration Date
    public boolean validateRegistrationDate(String[] registrationDatePieces) {
        int day = Integer.parseInt(registrationDatePieces[0]);
        int month = Integer.parseInt(registrationDatePieces[1]);
        int year = Integer.parseInt(registrationDatePieces[2]);

        if ((day < 1 || day > 31) || (month < 1 || month >= 12)) return false;

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day >= 1 && day <= 31) return true;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day <= 30) return true;
        } else if (month == 2) {
            if (year % 4 == 0) {
                if (!(year % 100 == 0 && year % 400 != 0)) {
                    if (day <= 29) return true;
                }
            }
            if (day <= 28) return true;
        }
        return false;
    }

    // Student email
    private boolean validateStudentEmail(String studentEmail, Map<String, Student> students) {
        return students.get(studentEmail) != null;
    }

    // Course name
    private boolean validateCourseName(String courseName, Map<String, Course> courses) {
        return courses.get(courseName) != null;
    }
}
