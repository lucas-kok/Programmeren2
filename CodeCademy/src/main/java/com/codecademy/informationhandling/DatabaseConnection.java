package com.codecademy.informationhandling;

import com.codecademy.Course;
import com.codecademy.Registration;
import com.codecademy.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {
    private final String connectionURL;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public DatabaseConnection() {
        connectionURL = "jdbc:sqlserver://localhost\\MSSQLSERVER;databaseName=CodeCademy;user=sa;password=LucasKokSQL;";
    }

    public void setUp() {

    }

    public Map<String, Student> getStudents() {
        System.out.println("Creating a list of Students");
        Map<String, Student> students = new HashMap<>();
        Student student = new Student("Lucas Kok", "lucas.kok@hotmail.nl", "Perenmeet 48", "4328 CM", "Burgh-Haamstede", "The Netherlands", "Male", "09-01-2005");
        students.put(student.getEmail(), student);

        return students;
    }

    public void addStudent(Student student) {
        String name = student.getName();
        String email = student.getEmail();
        String address = student.getAddress();
        String city = student.getCity();
        String country = student.getCountry();
        String gender = student.getGender();
        String birthday = student.getBirthday();
    }

    public void updateStudent(Student student) {

    }

    public void deleteStudent(Student student) {

    }

    public Map<String, Course> getCourses() {
        System.out.println("Creating a list of Courses");

        Map<String, Course> courses = new HashMap<>();
        Course course = new Course("Test Course", "A course for testing", "Welcome tot this test course where you can learn nothing!", "Beginner", null);
        courses.put(course.getName(), course);

        return courses;
    }

    public void addCourse(Course course) {

    }

    public void updateCourse(Course selectedCourse, String name, String subject, String introductionText, String level, String relatedCoursesString) {

    }

    public void deleteCourse(Course course) {

    }

    public ArrayList<Registration> getRegistrations() {
        System.out.println("Creating a list of Registrations");

        ArrayList<Registration> registrations = new ArrayList<>();
        Registration registration = new Registration("09-01-2005", "lucas.kok@hotmail.nl", "Test Course");
        registrations.add(registration);

        return registrations;
    }

    public void addRegistration(Registration registration) {

    }

    public void updateRegistration(Registration selectedRegistration, String registrationDate, String studentEmail, String courseName) {

    }

    public void deleteRegistration(Registration registration) {

    }
}
