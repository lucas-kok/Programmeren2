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

    public void createCertificate(Certificate certificate) {
        String queryCreateCertificate = "INSERT INTO Certificate VALUES (" + certificate.getRegisterID() + " , " + certificate.getScore() + ", '" + certificate.getStaffName() + "')";
        dbCon.setQuery(queryCreateCertificate);
    }

    public ArrayList<Certificate> getALlCertificates() throws SQLException {
        ArrayList<Certificate> certificates = new ArrayList<>();
        String queryGetAllCertificates = "SELECT * FROM Certificate";
        ResultSet rs = dbCon.getQuery(queryGetAllCertificates);
        while (rs.next()) {
            certificates.add(new Certificate(rs.getInt("CertificateID"), rs.getInt("RegisterID"), rs.getInt("Score"), rs.getString("StaffName")));
        }
        dbCon.CloseResultSet();
        return certificates;
    }

    public void updateCertificate(Certificate certificate, String staffName, int score) {
        String queryUpdateCertificate = "   UPDATE Certificate " +
                "                           SET StaffName = '" + staffName + "' " +
                "                           , Score = " + score + " " +
                "                           WHERE CertificateID = " + certificate.getCertificateID() + "";
        dbCon.setQuery(queryUpdateCertificate);
    }

    public void deleteCertificate(Certificate certificate) {
        String queryDeleteCertificate = "DELETE From Certificate" +
                "                       WHERE CertificateID = " + certificate.getCertificateID() + "";
    }

}
