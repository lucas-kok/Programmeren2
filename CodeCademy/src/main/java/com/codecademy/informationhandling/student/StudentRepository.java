package com.codecademy.informationhandling.student;

import com.codecademy.informationhandling.InformationFormatter;
import com.codecademy.informationhandling.certificate.Certificate;
import com.codecademy.informationhandling.databaseconnection.DatabaseConnection;
import com.codecademy.informationhandling.registration.Registration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentRepository {

    private final DatabaseConnection dbCon = new DatabaseConnection();
    private final InformationFormatter informationFormatter;

    public StudentRepository() {
        informationFormatter = new InformationFormatter();
    }

    // Function that creates a Student
    public void createStudent(Student student) {
        informationFormatter.formatStudent(student);

        String gender = "";
        switch (student.getGender()) {
            case "Male":
                gender = "m";
                break;
            case "Female":
                gender = "f";
                break;
            case "Other":
                gender = "x";
                break;
        }

        String query = "INSERT INTO Student VALUES ('" + student.getEmail() + "', '" + student.getName() + "', convert(datetime, '" + student.getBirthday() + "', 103)" +
                ", '" + gender + "', '" + student.getAddress() + "', '" + student.getCity() + "', '" + student.getCountry() + "', '" + student.getPostalCode() + "')";
        dbCon.setQuery(query);
    }

    // Function that returns all Students in the Database
    public HashMap<String, Student> getAllStudents() throws SQLException {
        HashMap<String, Student> studentsList = new HashMap<>();

        String query = "SELECT * FROM Student";
        ResultSet rs = dbCon.getQuery(query);
        while (rs.next()) {
            String email = rs.getString("Email");
            String name = rs.getString("Name");
            String birthday = rs.getDate("Birthday").toString();
            String gender = rs.getString("Gender");
            switch (gender) {
                case "m":
                    gender = "Male";
                    break;
                case "f":
                    gender = "Female";
                    break;
                case "x":
                    gender = "Other";
                    break;
            }

            String Address = rs.getString("Address");
            String City = rs.getString("City");
            String Country = rs.getString("Country");
            String PostalCode = rs.getString("PostalCode");

            studentsList.put(email, new Student(email, name, birthday, gender, Address, City, Country, PostalCode));
        }
        dbCon.CloseResultSet();

        return studentsList;
    }

    // Function that updates a given Student
    public void updateStudent(Student student, String name, String email, String address, String postalCode, String city, String country, String gender, String birthday) {
        switch (gender) {
            case "Male":
                gender = "m";
                break;
            case "Female":
                gender = "f";
                break;
            case "Other":
                gender = "x";
                break;
        }

        String oldEmail = student.getEmail();

        student.setEmail(email);
        student.setName(name);
        student.setAddress(address);
        student.setPostalCode(postalCode);
        student.setCity(city);
        student.setCountry(country);
        student.setGender(gender);
        student.setBirthday(birthday);
        informationFormatter.formatStudent(student);

        String query = "UPDATE Student " +
                "       SET Email = '" + student.getEmail() + "'" +
                "       , Name = '" + student.getName() + "'" +
                "       , Address = '" + student.getAddress() + "'" +
                "       , PostalCode = '" + student.getPostalCode() + "'" +
                "       , City = '" + student.getCity() + "'" +
                "       , Country = '" + student.getCountry() + "'" +
                "       , Gender = '" + gender + "'" +
                "       , Birthday = (convert(datetime, '" + student.getBirthday() + "', 103)) " +
                "       WHERE Email = '" + oldEmail + "'" +
                "       UPDATE Viewing SET StudentEmail = '" + student.getEmail() + "' WHERE StudentEmail = '" + oldEmail + "'" +
                "       UPDATE Register SET StudentEmail = '" + student.getEmail() + "' WHERE StudentEmail = '" + oldEmail + "'";
        dbCon.setQuery(query);
    }

    // Function that deletes a given Student
    public void deleteStudent(Student selectedStudent) {
        String query = "DELETE FROM Student" +
                "       WHERE Email = '" + selectedStudent.getEmail() + "'";
        dbCon.setQuery(query);
    }

    // Function that returns all Registrations linked to a given Student
    public ArrayList<Registration> getAllRegistrationsForStudent(Student student) throws SQLException {
        ArrayList<Registration> registrations = new ArrayList<>();

        String query = "SELECT * FROM Register WHERE StudentEmail = '" + student.getEmail() + "'";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            registrations.add(new Registration(rs.getInt("RegisterID"), rs.getString("StudentEmail"), rs.getString("RegisterDate"), rs.getString("CourseName")));
        }
        dbCon.CloseResultSet();

        return registrations;
    }

    // Function that returns all Certificates linked to a given Student
    public ArrayList<Certificate> getAllCertificatesForStudent(Student student) throws SQLException {
        ArrayList<Certificate> certificates = new ArrayList<>();

        String query = "SELECT Certificate.*, Register.StudentEmail, Register.CourseName FROM Certificate INNER JOIN Register ON Register.RegisterID = Certificate.RegisterID WHERE StudentEmail = '" + student.getEmail() + "'";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            certificates.add(new Certificate(rs.getInt("CertificateID"), rs.getString("StudentEmail"),rs.getString("CourseName"), rs.getInt("Score"), rs.getString("StaffName")));
        }
        dbCon.CloseResultSet();

        return certificates;
    }
}