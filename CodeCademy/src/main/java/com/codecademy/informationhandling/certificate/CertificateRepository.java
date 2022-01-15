package com.codecademy.informationhandling.certificate;

import com.codecademy.informationhandling.databaseconnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CertificateRepository {

    private final DatabaseConnection dbCon;

    public CertificateRepository() {
        dbCon = new DatabaseConnection();
    }

    public void createCertificate(String studentEmail, String courseName, int score, String StaffName) throws SQLException {
        int RegisterID = 0;

        String query = "SELECT RegisterID FROM Register WHERE StudentEmail = '" + studentEmail + "' AND CourseName = '" + courseName + "'";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            RegisterID = rs.getInt("RegisterID");
        }
        dbCon.CloseResultSet();

        if (RegisterID == 0) return;

        String queryCreateCertificate = "INSERT INTO Certificate VALUES (" + RegisterID + " , " + score + ", '" + StaffName + "')";
        dbCon.setQuery(queryCreateCertificate);
    }

    public ArrayList<Certificate> getALlCertificates() throws SQLException {
        ArrayList<Certificate> certificates = new ArrayList<>();

        String query = "SELECT Certificate.*, Register.StudentEmail, Register.CourseName FROM Certificate INNER JOIN Register ON Register.RegisterID = Certificate.RegisterID";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            certificates.add(new Certificate(rs.getInt("CertificateID"), rs.getInt("RegisterID"), rs.getString("StudentEmail"), rs.getString("CourseName"), rs.getInt("Score"), rs.getString("StaffName")));
        }
        dbCon.CloseResultSet();

        return certificates;
    }

    public void updateCertificate(Certificate certificate, String staffName, int score) {
        String query = "   UPDATE Certificate " +
                "                           SET StaffName = '" + staffName + "' " +
                "                           , Score = " + score + " " +
                "                           WHERE CertificateID = " + certificate.getCertificateID() + "";
        dbCon.setQuery(query);
    }

    public void deleteCertificate(Certificate certificate) {
        String query = "DELETE From Certificate" +
                "                       WHERE CertificateID = " + certificate.getCertificateID() + "";
    }
}
