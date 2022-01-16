package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.InformationFormatter;
import com.codecademy.informationhandling.course.Course;
import com.codecademy.informationhandling.course.CourseRepository;
import com.codecademy.informationhandling.registration.Registration;
import com.codecademy.informationhandling.registration.RegistrationRepository;
import com.codecademy.informationhandling.student.Student;
import com.codecademy.informationhandling.student.StudentRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class RegistrationInformationValidator {
    private final InformationFormatter informationFormatter;
    private final StudentRepository studentRepository;
    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;

    public RegistrationInformationValidator() {
        informationFormatter = new InformationFormatter();
        studentRepository = new StudentRepository();
        registrationRepository = new RegistrationRepository();
        courseRepository = new CourseRepository();
    }

    // Function to validate all inputs for a Registration and returns an error/empty message
    public String validateNewRegistration(String studentEmail, String courseName) throws SQLException {
        StringBuilder message = new StringBuilder();

        studentEmail = studentEmail.toLowerCase();
        courseName = informationFormatter.capitalizeString(courseName);

        if (!emailExists(studentEmail)) {
            message.append("\nThe email: '").append(studentEmail).append("' does not exists!");
        }

        if (!isValidCourseName(courseName)) {
            message.append("\nThe Course: '").append(courseName).append("' does not exists!");
        }

        if (registrationExists(studentEmail, courseName)) {
            message.append("\nThe registration already exists!");
        }

        return message.toString();
    }

    // Function to validate all inputs for an existing Registration and returns an error/empty message
    public String validateEditedRegistration(String[] registrationDatePieces, ArrayList<Integer> progression) {
        StringBuilder message = new StringBuilder();

        if (!isValidRegistrationDate(registrationDatePieces)) {
            message.append("\nThe Registration Date is not valid!");
        }

        if (!isValidProgression(progression)) {
            message.append("\nSome progression values are not between 0 and 100!");
        }

        return message.toString();
    }

    // Registration Date
    public boolean isValidRegistrationDate(String[] registrationDatePieces) {
        if (!isValidNumber(registrationDatePieces[0]) || !isValidNumber(registrationDatePieces[1]) || !isValidNumber(registrationDatePieces[2])) return false;

        int day = Integer.parseInt(registrationDatePieces[2]);
        int month = Integer.parseInt(registrationDatePieces[1]);
        int year = Integer.parseInt(registrationDatePieces[0]);

        if (year < 1000) return false;

        if ((day < 1 || day > 31) || (month < 1 || month > 12)) return false;

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return true;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day <= 30;
        } else {
            if (year % 4 == 0) {
                if (!(year % 100 == 0 && year % 400 != 0)) {
                    if (day <= 29) return true;
                }
            }
            return day <= 28;
        }
    }

    // Student Email
    private boolean emailExists(String studentEmail) throws SQLException {
        Map<String, Student> students = studentRepository.getAllStudents();
        return students.containsKey(studentEmail.toLowerCase());
    }

    // Course Name
    private boolean isValidCourseName(String courseName) throws SQLException {
        Map<String, Course> courses = courseRepository.getAllCourses();
        return courses.containsKey(informationFormatter.capitalizeString(courseName));
    }

    private boolean registrationExists(String studentEmail, String courseName) throws SQLException {
        Map<String, Registration> registrations = registrationRepository.getAllRegistrations();
        return registrations.containsKey(studentEmail + "-" + courseName);
    }

    // Progression
    private boolean isValidProgression(ArrayList<Integer> progression) {
        for (int progress : progression) {
            if (progress < 0 || progress > 100) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidNumber(String scoreString) {
        try {
            Integer.parseInt(scoreString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
