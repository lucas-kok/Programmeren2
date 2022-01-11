package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.Course.Course;
import com.codecademy.informationhandling.Student.Student;

import java.util.Map;

public class RegistrationInformationValidator {

    public RegistrationInformationValidator() {

    }

    public String validateNewRegistration(String studentEmail, String courseName, Map<String, Student> students, Map<String, Course> courses) {
        StringBuilder message = new StringBuilder();

        if (!validateStudentEmail(studentEmail, students)) message.append("The email: '").append(studentEmail).append("' does not exists!");
        if (!validateCourseName(courseName, courses)) message.append("The Course: '").append(courseName).append("' does not exists!");

        return message.toString();
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
