package com.codecademy.gui;

import com.codecademy.informationhandling.certificate.Certificate;
import com.codecademy.informationhandling.course.Course;
import com.codecademy.informationhandling.student.Student;
import com.codecademy.informationhandling.registration.Registration;

import java.util.ArrayList;
import java.util.Map;

public class SearchBar {

    public SearchBar() {
    }

    // Function that returns a List of Students matching the given search input
    public ArrayList<Student> searchStudents(String searchInput, Map<String, Student> students) {
        if (searchInput.isBlank())
            return new ArrayList<>(students.values()); // Empty search will result in all Students

        ArrayList<Student> studentsWithinInput = new ArrayList<>();
        searchInput = searchInput.toLowerCase();
        for (Student student : students.values()) {
            if (student.getEmail().contains(searchInput) || student.getName().toLowerCase().contains(searchInput)) {
                studentsWithinInput.add(student);
            }
        }

        return studentsWithinInput;
    }

    // Function that returns a List of Courses matching the given search input
    public ArrayList<Course> searchCourses(String searchInput, Map<String, Course> courses) {
        if (searchInput.isBlank()) return new ArrayList<>(courses.values()); // Empty search will result in all Courses

        ArrayList<Course> coursesWithinInput = new ArrayList<>();
        searchInput = searchInput.toLowerCase();
        for (Course course : courses.values()) {
            if (course.getName().toLowerCase().contains(searchInput) || course.getSubject().toLowerCase().contains(searchInput)) {
                coursesWithinInput.add(course);
            }
        }

        return coursesWithinInput;
    }

    // Function that returns a List of Certificates matching the given search input
    public ArrayList<Certificate> searchCertificates(String searchInput, ArrayList<Certificate> certificates) {
        if (searchInput.isBlank()) return certificates; // Empty search will result in all Registrations

        ArrayList<Certificate> certificatesWithInput = new ArrayList<>();
        searchInput = searchInput.toLowerCase();
        for (Certificate certificate : certificates) {
            if (certificate.getStudentEmail().toLowerCase().contains(searchInput) || certificate.getCourseName().toLowerCase().contains(searchInput) || String.valueOf(certificate.getScore()).contains(searchInput)) {
                certificatesWithInput.add(certificate);
            }
        }

        return certificatesWithInput;
    }

    // Function that returns a List of Registrations matching the given search input
    public ArrayList<Registration> searchRegistrations(String searchInput, Map<String, Registration> registrations) {
        if (searchInput.isBlank())
            return new ArrayList<>(registrations.values()); // Empty search will result in all Registrations

        ArrayList<Registration> registrationsWithinInput = new ArrayList<>();
        searchInput = searchInput.toLowerCase();
        for (Registration registration : registrations.values()) {
            if (registration.getRegistrationDateAsString().contains(searchInput) || registration.getRegistrationDate().toLowerCase().contains(searchInput) || registration.getStudentEmail().toLowerCase().contains(searchInput) ||
                    registration.getCourseName().toLowerCase().contains(searchInput)) {
                registrationsWithinInput.add(registration);
            }
        }

        return registrationsWithinInput;
    }
}
