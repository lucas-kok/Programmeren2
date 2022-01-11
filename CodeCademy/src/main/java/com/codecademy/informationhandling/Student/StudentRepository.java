package com.codecademy.informationhandling.Student;

import com.codecademy.informationhandling.Databaseconnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class StudentRepository {

    private DatabaseConnection dbCon = new DatabaseConnection();

    public StudentRepository() {
    }

    public void createStudent(Student student) {
        String gender = "";
        if (student.getGender().equals("Male")) {
            gender = "m";
        } else if (student.getGender().equals("Female")) {
            gender = "f";
        } else if (student.getGender().equals("Other")) {
            gender = "x";
        }

        String query = "INSERT INTO Student VALUES ('" + student.getEmail() + "', '" + student.getName() + "', convert(datetime, '" + student.getBirthday().toString().replaceAll("-", "/") + "', 103)" +
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
        String query = "UPDATE Student" +
                "SET Email = '" + email + "'" +
                ", Name = '" + name + "' " +
                ", Address = '" + address + "'" +
                ", PostalCode = '" + postalCode + "'" +
                ", City = '" + city + "'" +
                ", Country = '" + country + "'" +
                ", Gender = '" + gender + "'" +
                ", Birthday = convert(datetime, '" + birthday.toString().replaceAll("-", "/") + "'" +
                "WHERE Email = '" + selectedStudent.getEmail() + "'";
        dbCon.setQuery(query);
    }

    public void deleteStudent(Student selectedStudent) {

    }

}