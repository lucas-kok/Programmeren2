package com.codecademy.gui;

import com.codecademy.Course;
import com.codecademy.Registration;
import com.codecademy.Student;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class SearchBar {

    public SearchBar() {

    }

    public ArrayList<Student> searchStudents(String searchInput, Map<String, Student> students) {
        if (searchInput.isBlank()) return new ArrayList<>(students.values()); // Empty search will result in all Students

        ArrayList<Student> studentsWithinInput = new ArrayList<>();
        searchInput = searchInput.toLowerCase();
        for (Student student : students.values()) {
            if (student.getEmail().contains(searchInput) || student.getName().toLowerCase().contains(searchInput)) {
                studentsWithinInput.add(student);
            }
        }

        return studentsWithinInput;
    }

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

    public ArrayList<Registration> searchRegistrations(String searchInput, ArrayList<Registration> registrations) {
        if (searchInput.isBlank()) return registrations; // Empty search will result in all Registrations

        ArrayList<Registration> registrationsWithinInput = new ArrayList<>();
        searchInput = searchInput.toLowerCase();
        for (Registration registration : registrations) {
            if (registration.getRegistrationDate().toLowerCase().contains(searchInput) || registration.getStudentEmail().toLowerCase().contains(searchInput) ||
                    registration.getCourseName().toLowerCase().contains(searchInput)) {
                registrationsWithinInput.add(registration);
            }
        }

        return registrationsWithinInput;
    }

}
