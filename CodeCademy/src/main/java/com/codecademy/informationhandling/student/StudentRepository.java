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

    public void updateStudent(Student selectedStudent, String name, String email, String address, String postalCode, String city, String country, String gender, String birthday) {
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

        String oldEmail = selectedStudent.getEmail();

        selectedStudent.setEmail(email);
        selectedStudent.setName(name);
        selectedStudent.setAddress(address);
        selectedStudent.setPostalCode(postalCode);
        selectedStudent.setCity(city);
        selectedStudent.setCountry(country);
        selectedStudent.setGender(gender);
        selectedStudent.setBirthday(birthday);
        informationFormatter.formatStudent(selectedStudent);

        String query = "UPDATE Student " +
                "       SET Email = '" + selectedStudent.getEmail() + "'" +
                "       , Name = '" + selectedStudent.getName() + "'" +
                "       , Address = '" + selectedStudent.getAddress() + "'" +
                "       , PostalCode = '" + selectedStudent.getPostalCode() + "'" +
                "       , City = '" + selectedStudent.getCity() + "'" +
                "       , Country = '" + selectedStudent.getCountry() + "'" +
                "       , Gender = '" + gender + "'" +
                "       , Birthday = (convert(datetime, '" + selectedStudent.getBirthday() + "', 103)) " +
                "       WHERE Email = '" + oldEmail + "'" +
                "       UPDATE Viewing SET StudentEmail = '" + selectedStudent.getEmail() + "' WHERE StudentEmail = '" + oldEmail + "'" +
                "       UPDATE Register SET StudentEmail = '" + selectedStudent.getEmail() + "' WHERE StudentEmail = '" + oldEmail + "'";
        dbCon.setQuery(query);
    }

    public void deleteStudent(Student selectedStudent) {
        String query = "DELETE FROM Student" +
                "       WHERE Email = '" + selectedStudent.getEmail() + "'";
        dbCon.setQuery(query);
    }

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

    public ArrayList<Certificate> getAllCertificatesForStudent(Student student) throws SQLException {
        ArrayList<Certificate> certificates = new ArrayList<>();

        String query = "SELECT Certificate.*, Register.StudentEmail, Register.CourseName FROM Certificate INNER JOIN Register ON Register.RegisterID = Certificate.RegisterID WHERE StudentEmail = '" + student.getEmail() + "'";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            certificates.add(new Certificate(rs.getInt("CertificateID"), rs.getInt("RegisterID"), rs.getString("StudentEmail"),rs.getString("CourseName"), rs.getInt("Score"), rs.getString("StaffName")));
        }
        dbCon.CloseResultSet();

        return certificates;
    }
}