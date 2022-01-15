package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.certificate.Certificate;
import com.codecademy.informationhandling.certificate.CertificateRepository;
import com.codecademy.informationhandling.course.Course;
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

    public CertificateInformationValidator() {
        studentRepository = new StudentRepository();
        courseRepository = new CourseRepository();
        certificateRepository = new CertificateRepository();
    }

    public String validateEditedCertificate(int score) {
        if (score >= 1 && score <= 10) {
            return "";
        } else {
            return "Grade must be between 1 and 10!";
        }
    }

    public String validateNewCertificate(String studentEmail, String courseName, int score) throws SQLException {
        StringBuilder message = new StringBuilder();
        if (!(score >= 1 && score <= 10)) message.append("\n The grade must be between 1 and 10!");
        if (!(doesStudentExists(studentEmail))) message.append("\n Student does not exist!");
        if (!(doesCourseExists(courseName))) message.append("\n Course does not exist!");
        if (doesCertificateExists(studentEmail, courseName)) message.append("\n There is already a certificate for this Regsitration!");
        return message.toString();
    }

    private boolean doesStudentExists(String studentEmail) throws SQLException {
        HashMap<String, Student> studentList = studentRepository.getAllStudents();
        return studentList.containsKey(studentEmail);
    }

    private boolean doesCourseExists(String courseName) throws SQLException {
        Map<String, Course> courseList = courseRepository.getAllCourses();
        return courseList.containsKey(courseName);
    }

    private boolean doesCertificateExists(String studentEmail, String courseName) throws SQLException{
        ArrayList<Certificate> certificates = certificateRepository.getALlCertificates();
        for (Certificate certificate : certificates) {
            if (certificate.getStudentEmail().equals(studentEmail) && certificate.getCourseName().equals(courseName)) return true;
        }
        return false;
    }

}
