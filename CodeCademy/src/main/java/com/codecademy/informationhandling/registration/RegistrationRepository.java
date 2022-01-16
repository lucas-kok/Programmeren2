package com.codecademy.informationhandling.registration;

import com.codecademy.informationhandling.InformationFormatter;
import com.codecademy.informationhandling.contentitem.ContentItem;
import com.codecademy.informationhandling.databaseconnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationRepository {

    private final DatabaseConnection dbCon;
    private final InformationFormatter informationFormatter;

    public RegistrationRepository() {
        dbCon = new DatabaseConnection();
        informationFormatter = new InformationFormatter();
    }

    // Function that returns all Registrations in the Database
    public Map<String, Registration> getAllRegistrations() throws SQLException {
        Map<String, Registration> registrations = new HashMap<>();

        String queryGetAllRegistrations = "SELECT * FROM Register";
        ResultSet rs = dbCon.getQuery(queryGetAllRegistrations);

        while (rs.next()) {
            Registration newRegistration = new Registration(rs.getInt("RegisterID"), rs.getString("StudentEmail")
                    , rs.getDate("Registerdate").toString(), rs.getString("CourseName"));
            registrations.put(newRegistration.getStudentEmail() + "-" + newRegistration.getCourseName(), newRegistration);
        }
        dbCon.CloseResultSet();

        return registrations;
    }

    // Function that creates a new Registration
    public void createRegistration(String studentEmail, String courseName, String[] datePieces) throws SQLException {
        studentEmail = studentEmail.toLowerCase();
        courseName = informationFormatter.capitalizeString(courseName);
        String registrationDate = datePieces[2] + "/" + datePieces[1] + "/" + datePieces[0];

        String queryCreateRegistration = "INSERT INTO Register values (convert(datetime, '" + registrationDate + "', 103), '" + studentEmail + "', '" + courseName + "')";
        dbCon.setQuery(queryCreateRegistration);

        ArrayList<Integer> contentItemIDs = new ArrayList<>();

        String querygetAllContentItemsForCourse = "SELECT ContentID FROM ContentItem WHERE CourseName = '" + courseName + "'";
        ResultSet rs = dbCon.getQuery(querygetAllContentItemsForCourse);

        while (rs.next()) {
            contentItemIDs.add(rs.getInt("ContentID"));
        }
        dbCon.CloseResultSet();

        for (Integer id : contentItemIDs) {
            String queryAddProgressItemForRegistration = "INSERT INTO Viewing values ('" + studentEmail + "', " + id + ", 0)";
            dbCon.setQuery(queryAddProgressItemForRegistration);
        }
    }

    // Function that updates a given Registration
    public void updateRegistration(Registration registration, String[] datePieces) {
        String registrationDate = datePieces[2] + "/" + datePieces[1] + "/" + datePieces[0];
        String reversedRegistrationDate = datePieces[0] + "-" + datePieces[1] + "-" + datePieces[2];
        registration.setRegistrationDate(reversedRegistrationDate);

        String query = "UPDATE Register SET Registerdate = convert(datetime, '" + registrationDate + "', 103) WHERE RegisterID = " + registration.getRegisterID();
        dbCon.setQuery(query);
    }

    // Function that deletes a given Registration
    public void deleteRegistration(Registration registration) {
        String query = "DELETE FROM Register WHERE RegisterID = " + registration.getRegisterID() + " " +
                "                         DELETE FROM Viewing WHERE StudentEmail = '" + registration.getStudentEmail() + "' " +
                "                         AND ContenID IN (SELECT ContenID FROM ContentItem WHERE CourseName = '" + registration.getCourseName() + "')";
        dbCon.setQuery(query);
    }

    // Function that returns a Map of Content Items and the progression linked to a Registration
    public HashMap<ContentItem, Integer> getProgressForRegistration(Registration registration) throws SQLException {
        HashMap<ContentItem, Integer> progressPerContentItem = new HashMap<>();
        ArrayList<ContentItem> contentItems = new ArrayList<>();

        String queryGetAllContentItems = "SELECT * FROM ContentItem WHERE CourseName = '" + registration.getCourseName() + "'";
        ResultSet rs = dbCon.getQuery(queryGetAllContentItems);

        while (rs.next()) {
            contentItems.add(new ContentItem(rs.getInt("ContentID"), rs.getString("Title"), rs.getDate("PublicationDate").toString()));
        }
        dbCon.CloseResultSet();

        for (ContentItem contentItem : contentItems) {
            String queryGetProgress = "SELECT Progress FROM Viewing WHERE StudentEmail = '" + registration.getStudentEmail() + "' AND ContenID = " + contentItem.getId();
            ResultSet rsProgress = dbCon.getQuery(queryGetProgress);

            while (rsProgress.next()) {
                progressPerContentItem.put(contentItem, rsProgress.getInt("Progress"));
            }
            dbCon.CloseResultSet();
        }

        return progressPerContentItem;
    }

    // Function that updates all progression for all Content Items linked to a Registration
    public void updateProgress(Registration registration, Map<ContentItem, Integer> newProgression) {
        for (ContentItem contentItem : newProgression.keySet()) {
            int progress = newProgression.get(contentItem);

            String query = "UPDATE Viewing SET Progress = " + progress + " WHERE StudentEmail = '" + registration.getStudentEmail() + "' AND ContenID = " + contentItem.getId() + " ";
            dbCon.setQuery(query);
        }
    }
}
