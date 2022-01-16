package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.course.Course;
import com.codecademy.informationhandling.InformationFormatter;
import com.codecademy.informationhandling.course.CourseRepository;
import com.codecademy.informationhandling.registration.Registration;
import com.codecademy.informationhandling.registration.RegistrationRepository;

import java.sql.SQLException;
import java.util.Map;

public class CourseInformationValidator {
    private final InformationFormatter informationFormatter;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    public CourseInformationValidator() {
        informationFormatter = new InformationFormatter();
        courseRepository = new CourseRepository();
        registrationRepository = new RegistrationRepository();
    }

    // Function to validate information for a new Course and will return an error/empty message
    public String validateNewCourse(String courseName, String relatedCoursesString) throws SQLException{
        StringBuilder message = new StringBuilder();

        if (courseNameExists(courseName)) {
            message.append("\nThe name: ").append(courseName).append(" already exists!");
        }

        if (!areValidRelatedCourses(relatedCoursesString)) {
            message.append("\nThe related Course(s) could not be found!");
        }

        return message.toString();
    }

    // Function to validate information to edit an existing Course and return an error/empty message
    public String validateEditedCourse(String courseName, String relatedCoursesString, Course selectedCourse) throws SQLException{
        StringBuilder message = new StringBuilder();

        if (!courseName.equals(selectedCourse.getName())) { // Course has his own name (Already exists, but still valid)
            if (courseNameExists(courseName)) {
                message.append("\nThe name: ").append(courseName).append(" already exists!");
            }
        }

        if (!areValidRelatedCourses(relatedCoursesString)) {
            message.append("\nThe related Course(s) could not be found!");
        }

        return message.toString();
    }

    // Function that returns if the given Course exists
    public boolean courseNameExists(String courseName) throws SQLException {
        Map<String, Course> courses = courseRepository.getAllCourses();
        courseName = informationFormatter.capitalizeString(courseName);

        return courses.containsKey(courseName);
    }

    // Function that returns if the given Related Courses are existing Courses
    public boolean areValidRelatedCourses(String relatedCoursesString) throws SQLException {
        if (relatedCoursesString.isBlank()) return true; // Course doesn't need to contain related courses

        Map<String, Course> courses = courseRepository.getAllCourses();
        String[] courseNames = relatedCoursesString.split(", ");
        for (String course : courseNames) {
            if (!courses.containsKey(course)) return false;
        }

        return true;
    }

    // Function that returns if the given Course has Registrations linked to it
    public boolean courseHasRegistrations(String courseName) throws SQLException {
        Map<String, Registration> registrations = registrationRepository.getAllRegistrations();

        for (Registration registration : registrations.values()) {
            if (registration.getCourseName().equals(courseName)) {
                return true;
            }
        }

        return false;
    }
}
