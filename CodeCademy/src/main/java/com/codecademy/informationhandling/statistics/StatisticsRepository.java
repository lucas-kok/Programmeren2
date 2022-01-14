package com.codecademy.informationhandling.statistics;

import com.codecademy.informationhandling.databaseconnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsRepository {

    private final DatabaseConnection dbCon;

    public StatisticsRepository() {
        dbCon = new DatabaseConnection();
    }

    public String[] getCertificatePercentage(String gender) throws SQLException {
        String[] successRate = new String[3];
        int amountOfCertificates = 0;
        int amountOfRegistrations = 0;

        String queryGetAmountOfCertificates = "SELECT COUNT(Certificate.RegisterID) as 'Certificates' FROM Certificate " +
                "INNER JOIN Register ON Register.RegisterID = Certificate.RegisterID " +
                "INNER JOIN Student ON Student.Email = Register.StudentEmail " +
                "WHERE Student.Gender = '" + gender + "'";
        ResultSet rsGetAmountOfCertificates = dbCon.getQuery(queryGetAmountOfCertificates);
        while (rsGetAmountOfCertificates.next()) {
            amountOfCertificates = rsGetAmountOfCertificates.getInt(1);
        }
        dbCon.CloseResultSet();

        String queryGetAmountOfRegistrations = "SELECT COUNT(Register.RegisterID) as 'Registrations' FROM Register " +
                "INNER JOIN Student ON Student.Email = Register.StudentEmail " +
                "WHERE Student.Gender = '" + gender + "'";
        ResultSet rsGetAmountOfRegistrations = dbCon.getQuery(queryGetAmountOfRegistrations);
        while (rsGetAmountOfRegistrations.next()) {
            amountOfRegistrations = rsGetAmountOfRegistrations.getInt(1);
        }
        dbCon.CloseResultSet();

        double successRateInt = ((double) amountOfCertificates / amountOfRegistrations) * 100;
        successRate[0] = String.valueOf(amountOfRegistrations);
        successRate[1] = String.valueOf(amountOfCertificates);
        successRate[2] = String.valueOf(Math.round(successRateInt));

        return successRate;
    }

}
