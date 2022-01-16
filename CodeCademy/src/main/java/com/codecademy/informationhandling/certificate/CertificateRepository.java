package com.codecademy.informationhandling.certificate;

import com.codecademy.informationhandling.InformationFormatter;
import com.codecademy.informationhandling.databaseconnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CertificateRepository {

    private final DatabaseConnection dbCon;
    private final InformationFormatter informationFormatter;

    public CertificateRepository() {
        dbCon = new DatabaseConnection();
        informationFormatter = new InformationFormatter();
    }

    // Function that creates a new Certificate
    public void createCertificate(String studentEmail, String courseName, int score, String staffName) throws SQLException {
        studentEmail = studentEmail.toLowerCase();
        courseName = informationFormatter.capitalizeString(courseName);
        staffName = informationFormatter.capitalizeString(staffName);
        int RegisterID = 0;

        String query = "SELECT RegisterID FROM Register WHERE StudentEmail = '" + studentEmail + "' AND CourseName = '" + courseName + "'";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            RegisterID = rs.getInt("RegisterID");
        }
        dbCon.CloseResultSet();

        if (RegisterID == 0) return;

        String queryCreateCertificate = "INSERT INTO Certificate VALUES (" + RegisterID + " , " + score + ", '" + staffName + "')";
        dbCon.setQuery(queryCreateCertificate);
    }

    // Function that returns all Certificates in the Database
    public ArrayList<Certificate> getALlCertificates() throws SQLException {
        ArrayList<Certificate> certificates = new ArrayList<>();

        String query = "SELECT Certificate.*, Register.StudentEmail, Register.CourseName FROM Certificate INNER JOIN Register ON Register.RegisterID = Certificate.RegisterID";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            certificates.add(new Certificate(rs.getInt("CertificateID"), rs.getString("StudentEmail"), rs.getString("CourseName"), rs.getInt("Score"), rs.getString("StaffName")));
        }
        dbCon.CloseResultSet();

        return certificates;
    }

    // Function that updates a given Certificate
    public void updateCertificate(Certificate certificate, String staffName, int score) {
        staffName = informationFormatter.capitalizeString(staffName);

        certificate.setStaffName(staffName);
        certificate.setScore(score);

        String query = "   UPDATE Certificate " +
                "                           SET StaffName = '" + certificate.getStaffName() + "' " +
                "                           , Score = " + certificate.getScore() + " " +
                "                           WHERE CertificateID = " + certificate.getCertificateID() + "";
        dbCon.setQuery(query);
    }

    // Function that deletes a given Certificate
    public void deleteCertificate(Certificate certificate) {
        String query = "DELETE From Certificate" +
                "                       WHERE CertificateID = " + certificate.getCertificateID() + "";
        dbCon.setQuery(query);
    }
}
