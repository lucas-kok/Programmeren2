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

    public String validateNewCertificate(String studentEmail, String courseName, int score) throws SQLException {
        StringBuilder message = new StringBuilder();

        if (!(score >= 1 && score <= 10)) message.append("\nThe grade must be between 1 and 10!");
        if (!studentExists(studentEmail)) message.append("\nStudent does not exist!");
        if (!courseExists(courseName)) message.append("\nCourse does not exist!");
        if (registrationExists(studentEmail, courseName)) {
            if (!studentHasCompletedCourse(studentEmail, courseName)) message.append("\nThe Student has not completed all Modules for this Course!");
        } else {
            message.append("\nThere is no Registration for this combination!");
        }
        if (certificateExists(studentEmail, courseName)) message.append("\nThere is already a Certificate for this Registration!");

        return message.toString();
    }

    public String validateEditedCertificate(int score) {
        if (score >= 1 && score <= 10) {
            return "";
        } else {
            return "Grade must be between 1 and 10!";
        }
    }

    private boolean studentExists(String studentEmail) throws SQLException {
        HashMap<String, Student> studentList = studentRepository.getAllStudents();
        return studentList.containsKey(studentEmail);
    }

    private boolean courseExists(String courseName) throws SQLException {
        Map<String, Course> courseList = courseRepository.getAllCourses();
        return courseList.containsKey(courseName);
    }

    private boolean registrationExists(String studentEmail, String courseName) throws SQLException {
        Map<String, Registration> registrations = registrationRepository.getAllRegistrations();
        return registrations.get(studentEmail + "-" + courseName) != null;
    }

    private boolean studentHasCompletedCourse(String studentEmail, String courseName) throws SQLException{
        Map<String, Registration> registrations = registrationRepository.getAllRegistrations();
        Map<ContentItem, Integer> contentItems = registrationRepository.getProgressForRegistration(registrations.get(studentEmail + "-" + courseName));

        for (Integer progress : contentItems.values()) {
            if (progress < 100) return false;
            System.out.println(progress);
        }

        return true;
    }

    private boolean certificateExists(String studentEmail, String courseName) throws SQLException{
        ArrayList<Certificate> certificates = certificateRepository.getALlCertificates();

        for (Certificate certificate : certificates) {
            if (certificate.getStudentEmail().equals(studentEmail) && certificate.getCourseName().equals(courseName)) return true;
        }

        return false;
    }
}
