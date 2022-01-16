package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.certificate.Certificate;
import com.codecademy.informationhandling.certificate.CertificateRepository;
import com.codecademy.informationhandling.contentitem.ContentItem;
import com.codecademy.informationhandling.course.Course;
import com.codecademy.informationhandling.registration.Registration;
import com.codecademy.informationhandling.registration.RegistrationRepository;
import com.codecademy.informationhandling.student.Student;
import com.codecademy.informationhandling.student.StudentRepository;
import com.codecademy.informationhandling.course.CourseRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CertificateInformationValidator {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CertificateRepository certificateRepository;
    private final RegistrationRepository registrationRepository;

    public CertificateInformationValidator() {
        studentRepository = new StudentRepository();
        courseRepository = new CourseRepository();
        certificateRepository = new CertificateRepository();
        registrationRepository = new RegistrationRepository();
    }

    // Function to validate all inputs for a new Certificate and returns an error/empty message
    public String validateNewCertificate(String studentEmail, String courseName, String score) throws SQLException {
        StringBuilder message = new StringBuilder();

        if (isValidNumber(score)) {
            if (!isValidScore(Integer.parseInt(score))) {
                message.append("\nThe grade must be between 1 and 10!");
            }
        } else {
            message.append("\nThe score: '").append(score).append("' is not valid!");
        }

        if (!studentExists(studentEmail)) {
            message.append("\nStudent does not exist!");
        }

        if (!courseExists(courseName)) {
            message.append("\nCourse does not exist!");
        }

        if (registrationExists(studentEmail, courseName)) {
            if (!studentHasCompletedCourse(studentEmail, courseName)) {
                message.append("\nThe Student has not completed all Modules for this Course!");
            }
        } else {
            message.append("\nThere is no Registration for this combination!");
        }

        if (certificateExists(studentEmail, courseName)) {
            message.append("\nThere is already a Certificate for this Registration!");
        }

        return message.toString();
    }

    // Function to validate all inputs for an existing Certificate and returns an error/empty message
    public String validateEditedCertificate(String scoreString) {
        StringBuilder message = new StringBuilder();

        if (isValidNumber(scoreString)) {
            if (!isValidScore(Integer.parseInt(scoreString))) {
                message.append("\nThe grade must be between 1 and 10!");
            }
        } else {
            message.append("\nThe score: '").append(scoreString).append("' is not valid!");
        }

        return message.toString();
    }

    // Function that returns if the given String are numbers
    private boolean isValidNumber(String scoreString) {
        try {
            Integer.parseInt(scoreString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Function that returns if the given Certificate score is valid
    public boolean isValidScore(int score) {
        return score >= 1 && score <= 10;
    }

    // Function that returns if the given Student exists
    private boolean studentExists(String studentEmail) throws SQLException {
        HashMap<String, Student> studentList = studentRepository.getAllStudents();
        return studentList.containsKey(studentEmail);
    }

    // Function that returns if the given Course exists
    private boolean courseExists(String courseName) throws SQLException {
        Map<String, Course> courseList = courseRepository.getAllCourses();
        return courseList.containsKey(courseName);
    }

    // Function that returns if a Registration for the given Student email and Course name already exists
    private boolean registrationExists(String studentEmail, String courseName) throws SQLException {
        Map<String, Registration> registrations = registrationRepository.getAllRegistrations();
        return registrations.containsKey(studentEmail + "-" + courseName);
    }

    // Function that returns if the given Student has completed all Content Items for the given Student and Course
    private boolean studentHasCompletedCourse(String studentEmail, String courseName) throws SQLException {
        Map<String, Registration> registrations = registrationRepository.getAllRegistrations();
        Map<ContentItem, Integer> contentItems = registrationRepository.getProgressForRegistration(registrations.get(studentEmail + "-" + courseName));

        for (Integer progress : contentItems.values()) {
            if (progress < 100) return false;
        }

        return true;
    }

    // Function that returns if a Certificate for the given Student email and Course name already exists
    private boolean certificateExists(String studentEmail, String courseName) throws SQLException {
        ArrayList<Certificate> certificates = certificateRepository.getALlCertificates();

        for (Certificate certificate : certificates) {
            if (certificate.getStudentEmail().equals(studentEmail) && certificate.getCourseName().equals(courseName)) return true;
        }

        return false;
    }
}
