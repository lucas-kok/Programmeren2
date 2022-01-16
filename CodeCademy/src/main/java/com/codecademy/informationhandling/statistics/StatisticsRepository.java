package com.codecademy.informationhandling.statistics;

import com.codecademy.informationhandling.databaseconnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsRepository {

    private final DatabaseConnection dbCon;

    public StatisticsRepository() {
        dbCon = new DatabaseConnection();
    }

    // Function that returns an array containing the number of Certificates and Registrations for a specific gender
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

    // Function that returns an array of the 3 Courses with the most Registrations
    public String[] getTopThreeCourses() throws SQLException {
        String[] topThreeCourses = new String[3];

        String query = "SELECT Course.CourseName, COUNT(CertificateID) FROM Course " +
                "LEFT JOIN Register ON Register.CourseName = Course.Coursename " +
                "LEFT JOIN Certificate ON Certificate.RegisterID = Register.RegisterID " +
                "GROUP BY Course.Coursename " +
                "ORDER BY COUNT(CertificateID) DESC";
        ResultSet rsTopThreeCourses = dbCon.getQuery(query);

        int index = 0;
        while (rsTopThreeCourses.next()) {
            if (index <= 2) {
                String courseAndNumber = rsTopThreeCourses.getString(1) + " (" + rsTopThreeCourses.getString(2) + ")";
                topThreeCourses[index] = courseAndNumber;
                index++;
            }
        }
        dbCon.CloseResultSet();

        return topThreeCourses;
    }

    // Function that returns an array of the 3 most viewed Webcasts
    public String[] getTopThreeWebcasts() throws SQLException {
        String[] topThreeWebcasts = {"No other webcasts with views", "No other webcasts with views", "No other webcasts with views"};

        String query = "SELECT Title, COUNT(Progress) FROM ContentItem " +
                "INNER JOIN Viewing ON ContentItem.ContentID = Viewing.ContenID " +
                "WHERE Viewing.ContenID IN (SELECT ContentID FROM Webcast) " +
                "AND Progress != 0 " +
                "GROUP BY Title " +
                "ORDER BY COUNT(StudentEmail) DESC";
        ResultSet rsTopThreeWebcasts = dbCon.getQuery(query);

        int index = 0;
        while (rsTopThreeWebcasts.next()) {
            if (index <= 2) {
                String webCastAndViews = rsTopThreeWebcasts.getString(1) + " (" + rsTopThreeWebcasts.getString(2) + ")";
                topThreeWebcasts[index] = webCastAndViews;
                index++;
            }
        }
        dbCon.CloseResultSet();

        return topThreeWebcasts;
    }
}
