package com.codecademy.informationhandling.Student;

import com.codecademy.informationhandling.Certificate.Certificate;
import com.codecademy.informationhandling.Databaseconnection.DatabaseConnection;
import com.codecademy.informationhandling.InformationFormatter;
import com.codecademy.informationhandling.Registration.Registration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentRepository {

    private DatabaseConnection dbCon = new DatabaseConnection();
    private final InformationFormatter informationFormatter;

    public StudentRepository() {
        informationFormatter = new InformationFormatter();
    }

    public void createStudent(Student student) {
        informationFormatter.formatStudent(student);

        String gender = "";
        if (student.getGender().equals("Male")) {
            gender = "m";
        } else if (student.getGender().equals("Female")) {
            gender = "f";
        } else if (student.getGender().equals("Other")) {
            gender = "x";
        }

        String query = "INSERT INTO Student VALUES ('" + student.getEmail() + "', '" + student.getName() + "', convert(datetime, '" + student.getBirthday() + "', 103)" +
                ", '" + gender + "', '" + student.getAddress() + "', '" + student.getCity() + "', '" + student.getCountry() + "', '" + student.getPostalCode() + "')";
        dbCon.setQuery(query);
    }

    public HashMap<String, Student> getAllStudents() throws SQLException {
        HashMap<String, Student> studentList = new HashMap<>();
        String query = "SELECT * FROM Student";
        ResultSet rs = dbCon.getQuery(query);
        while (rs.next()) {
            String Email = rs.getString("Email");
            String Name = rs.getString("Name");
            LocalDate Birthday = LocalDate.parse(rs.getDate("Birthday").toString());
            String gender = rs.getString("Gender");
            if (gender.equals("m")) {
                gender = "Male";
            } else if (gender.equals("f")) {
                gender = "Female";
            } else if (gender.equals("x")) {
                gender = "Other";
            }
            String Address = rs.getString("Address");
            String City = rs.getString("City");
            String Country = rs.getString("Country");
            String PostalCode = rs.getString("PostalCode");

            studentList.put(Email, new Student(Email, Name, Birthday, gender, Address, City, Country, PostalCode));
        }
        dbCon.CloseResultSet();
        return studentList;
    }

    public void updateStudent(Student selectedStudent, String name, String email, String address, String postalCode, String city, String country, String gender, LocalDate birthday) {
        if (gender.equals("Male")) {
            gender = "m";
        } else if (gender.equals("Female")) {
            gender = "f";
        } else if (gender.equals("Other")) {
            gender = "x";
        }

        Student newStudent = new Student(email, name, birthday, gender, address, city, country, postalCode);
        informationFormatter.formatStudent(newStudent);

        String query = "UPDATE Student " +
                "       SET Email = '" + newStudent.getEmail() + "'" +
                "       , Name = '" + newStudent.getName() + "'" +
                "       , Address = '" + newStudent.getAddress() + "'" +
                "       , PostalCode = '" + newStudent.getPostalCode() + "'" +
                "       , City = '" + newStudent.getCity() + "'" +
                "       , Country = '" + newStudent.getCountry() + "'" +
                "       , Gender = '" + gender + "'" +
                "       , Birthday = (convert(datetime, '" + newStudent.getBirthday() + "', 103)) " +
                "       WHERE Email = '" + selectedStudent.getEmail() + "'" +
                "       UPDATE Viewing SET StudentEmail = '" + newStudent.getEmail() + "' WHERE StudentEmail = '" + selectedStudent.getEmail() + "'";
        dbCon.setQuery(query);
    }

    public void deleteStudent(Student selectedStudent) {
        String query = "DELETE FROM Student" +
                "       WHERE Email = '" + selectedStudent.getEmail() + "'";
        dbCon.setQuery(query);
    }

    //Get all registrations and get all certificates
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
        String query = "SELECT * FROM Certificate WHERE RegisterID IN (SELECT * FROM Register WHERE StudentEmail = '" + student.getEmail() + "')";
        ResultSet rs = dbCon.getQuery(query);
        while (rs.next()) {
            certificates.add(new Certificate(rs.getInt("CertificateID"),rs.getInt("RegisterID"),rs.getInt("Score"),rs.getString("StaffName")));
        }
        dbCon.CloseResultSet();
        return certificates;
    }

}