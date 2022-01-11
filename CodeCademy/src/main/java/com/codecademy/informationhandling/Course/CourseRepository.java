package com.codecademy.informationhandling.Course;

import java.util.HashMap;
import java.util.Map;

public class CourseRepository {

    public static void addCourse(Course newCourse) {
    }

    public Map<String, Course> getAllCourses() {
        Map<String, Course> map = new HashMap<>();
        return map;
    }

    public void deleteCourse(Course selectedCourse) {
    }

    public void updateCourse(Course selectedCourse, String name, String subject, String introductionText, String level, String relatedCourses) {
    }

    public void createNewCourse(String name, String subject, String introductionText, String level, String relatedCoursesString) {
    }
}
