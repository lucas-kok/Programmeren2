package com.codecademy.informationhandling.Student;

import DatabaseConnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class StudentRepository {

    private DatabaseConnection dbCon = new DatabaseConnection();

    public StudentRepository() {
    }

    public void createStudent(Student student) {
        // Maak 1 letter van gender
        String query = "INSERT INTO Student VALUES ('" + student.getEmail() + "', '" + student.getName() + "', convert(datetime, '" + student.getBirthday().toString().replaceAll("-", "/") + "', 103)" +
                ", '" + student.getGender() + "', '" + student.getAddress() + "', '" + student.getCity() + "', '" + student.getCountry() + "')";
        dbCon.setQuery(query);
    }

    public HashMap<String, Student> getAllStudents() throws SQLException {
        //Maak een woord van gender
        HashMap<String, Student> studentList = new HashMap<>();
        String query = "SELECT * FROM Student";
        ResultSet rs = dbCon.getQuery(query);
        while (rs.next()) {
            System.out.println(rs.getString("Name"));
        }
        dbCon.CloseResultSet();
        return studentList;
    }

    public void updateStudent(Student selectedStudent, String name, String email, String address, String postalCode, String city, String country, String gender, LocalDate birthday) {
        if (gender.equals)
        String query = "UPDATE Student" +
                "SET Email = '" + email + "'" +
                ", Name = '" + name + "' " +
                ", Address = '" + address + "'" +
                ", PostalCode = '" + postalCode + "'" +
                ", City = '" + city + "'" +
                ", Country = '" + country + "'" +
                ", Gender = ";


    }

    public void deleteStudent() {

    }

}