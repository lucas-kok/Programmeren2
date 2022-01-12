package com.codecademy.informationhandling.Registration;

import com.codecademy.informationhandling.Databaseconnection.DatabaseConnection;
import com.codecademy.informationhandling.Student.Student;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class RegistrationRepository {

    private final DatabaseConnection dbCon;

    public RegistrationRepository() {
        dbCon = new DatabaseConnection();
    }

    public void createRegistration(Student student) {

    }

//    public HashMap<String, ArrayList<Registration>> getAllRegistrations() throws SQLException {
//        ArrayList<Registration> registrations = new ArrayList<>();
//        String queryGetAllRegistrations = "SELECT * FROM Registration";
//        ResultSet rs = dbCon.getQuery(queryGetAllRegistrations);
//        while (rs.next()) {
//            registrations.add(new Registration(rs.getInt("RegisterID"), rs.getString("StudentEmail"),
//                    rs.getString("Registerdate"), rs.getString("CourseName")));
//        }
//        return new HashMap<>();
//    }

    public ArrayList<Registration> getAllRegistrations() {
        return new ArrayList<>();
    }

    public void updateRegistration(Student selectedStudent, String name, String email, String address, String postalCode, String city, String country, String gender, LocalDate birthday) {

    }

    public void deleteRegistration(Student selectedStudent) {

    }

}
