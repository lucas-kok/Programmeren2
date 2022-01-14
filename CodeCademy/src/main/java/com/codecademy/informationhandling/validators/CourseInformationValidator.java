package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.course.Course;
import com.codecademy.informationhandling.InformationFormatter;
import com.codecademy.informationhandling.registration.Registration;

import java.util.Map;

public class CourseInformationValidator {
    private final InformationFormatter informationFormatter;

    public CourseInformationValidator() {
        informationFormatter = new InformationFormatter();
    }

    // Function to validate information for a new Course and will return an error/empty message
    public String validateNewCourse(String name, String relatedCoursesString, Map<String, Course> courses) {
        StringBuilder message = new StringBuilder();

        if (!validateName(name, courses)) message.append("\nThe name: ").append(name).append(" already exists!");
        if (!validateRelatedCourses(relatedCoursesString, courses))
            message.append("\nThe related Course(s) could not be found!");

        return message.toString();
    }

    // Function to validate information to edit an existing Course and return an error/empty message
    public String validateEditedCourse(String name, String relatedCoursesString, Map<String, Course> courses, Course selectedCourse) {
        StringBuilder message = new StringBuilder();

        if (!name.equals(selectedCourse.getName())) {
            if (!validateName(name, courses)) message.append("\nThe name: ").append(name).append(" already exists!");
        }
        if (!validateRelatedCourses(relatedCoursesString, courses))
            message.append("\nThe related Course(s) could not be found!");

        return message.toString();
    }

    // Name
    public boolean validateName(String name, Map<String, Course> courses) {
        String formattedName = informationFormatter.capitalizeString(name);
        return courses.get(formattedName) == null;
    }

    // Related Courses
    public boolean validateRelatedCourses(String relatedCoursesString, Map<String, Course> courses) {
        if (relatedCoursesString.isBlank()) return true; // Course doesn't need to contain related courses

        boolean valid = true;

        String[] courseNames = relatedCoursesString.split(", ");
        for (String course : courseNames) {
            if (courses.get(course) == null) valid = false;
        }

        return valid;
    }

    public boolean courseHasRegistrations(String courseName, Map<String, Registration> registrations) {
        for (Registration registration : registrations.values()) {
            if (registration.getCourseName().equals(courseName)) {
                return true;
            }
        }

        return false;
    }

}
