package com.codecademy.informationhandling;

import com.codecademy.informationhandling.Course.Course;
import com.codecademy.informationhandling.
import com.codecademy.Registration;
import com.codecademy.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class InformationHandler {
    private final DatabaseConnection databaseConnection;
    private final InformationFormatter informationFormatter;

    public InformationHandler() {
        databaseConnection = new DatabaseConnection();
        informationFormatter = new InformationFormatter();
    }

    // Students
    public Map<String, Student> getStudents() {
        return databaseConnection.getStudents();
    }

    public void createNewStudent(String name, String email, String address, String postalCode, String city, String country, String gender, LocalDate birthday) {
        String birthdayString = birthday.toString();
        System.out.println(birthdayString);

        Student newStudent = new Student(name, email, address, postalCode, city, country, gender, birthdayString);
        informationFormatter.formatStudent(newStudent);
        System.out.println("New Student: " + newStudent);

        // Add student to database
    }

    public void updateStudent(Student selectedStudent, String name, String email, String address, String postalCode, String city, String country, String gender, LocalDate birthday) {
        String birthdayString = birthday.toString();

        // Update student
        databaseConnection.updateStudent(selectedStudent);
    }

    public void deleteStudent(Student selectedStudent) {
        // Delete Student
        databaseConnection.deleteStudent(selectedStudent);
    }

    // Courses
    public Map<String, Course> getCourses() {
        return databaseConnection.getCourses();
    }

    public void createNewCourse(String name, String subject, String introductionText, String level, String relatedCoursesString) {
        String[] relatedCoursesPieces = relatedCoursesString.split(", ");
        ArrayList<Course> relatedCourses = new ArrayList<>();

        Map<String, Course> courses = getCourses();
        for (String relatedCoursePiece : relatedCoursesPieces) {
            if (courses.get(relatedCoursePiece) != null) { // In case an invalid name slips trough
                relatedCourses.add(courses.get(relatedCoursePiece));
            }
        }

        Course newCourse = new Course(name, subject, introductionText, level, relatedCourses);
        informationFormatter.formatCourse(newCourse);
        databaseConnection.addCourse(newCourse);
    }

    public void updateCourse(Course selectedCourse, String name, String subject, String introductionText, String level, String relatedCoursesString) {
        // Update Student
        databaseConnection.updateCourse(selectedCourse, name, subject, introductionText, level, relatedCoursesString);
    }

    public void deleteCourse(Course selectedCourse) {
        // Delete Course
        databaseConnection.deleteCourse(selectedCourse);
    }

    public ArrayList<Registration> getRegistrations() {
        return databaseConnection.getRegistrations();
    }

}
