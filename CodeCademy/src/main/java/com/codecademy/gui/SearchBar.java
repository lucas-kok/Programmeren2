package com.codecademy.gui;

import com.codecademy.Course;
import com.codecademy.Student;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class SearchBar {

    public SearchBar() {

    }

    public ArrayList<Student> searchStudents(String searchInput, Map<String, Student> students) {
        if (searchInput.isBlank()) return new ArrayList<>(students.values()); // Empty search will result in all students

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
        if (searchInput.isBlank()) return new ArrayList<>(courses.values()); // Empty search will result in all courses

        ArrayList<Course> coursesWithinInput = new ArrayList<>();
        searchInput = searchInput.toLowerCase();
        for (Course course : courses.values()) {
            if (course.getName().toLowerCase().contains(searchInput) || course.getSubject().toLowerCase().contains(searchInput)) {
                coursesWithinInput.add(course);
            }
        }

        return coursesWithinInput;
    }

}
