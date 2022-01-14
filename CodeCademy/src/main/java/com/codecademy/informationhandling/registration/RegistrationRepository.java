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

    public void createRegistration(String studentEmail, String courseName, String[] datePieces) throws SQLException {
        studentEmail = studentEmail.toLowerCase();
        courseName = informationFormatter.capitalizeString(courseName);
        String date = datePieces[2] + "/" + datePieces[1] + "/" + datePieces[0];

        String queryCreateRegistration = "INSERT INTO Register values (convert(datetime, '" + date + "', 103), '" + studentEmail + "', '" + courseName + "')";
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

    public void updateRegistration(Registration registration, String[] datePieces) {
        String date = datePieces[2] + "/" + datePieces[1] + "/" + datePieces[0];
        String query = "UPDATE Register SET Registerdate = convert(datetime, '" + date + "', 103) WHERE RegisterID = " + registration.getRegisterID();
        dbCon.setQuery(query);
    }

    public void deleteRegistration(Registration registration) {
        String queryDeleteRegistration = "DELETE FROM Register WHERE RegisterID = " + registration.getRegisterID() + " " +
                "                         DELETE FROM Viewing WHERE StudentEmail = '" + registration.getStudentEmail() + "' " +
                "                         AND ContenID IN (SELECT ContenID FROM ContentItem WHERE CourseName = '" + registration.getCourseName() + "')";
        dbCon.setQuery(queryDeleteRegistration);
    }

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
            System.out.println(queryGetProgress);
            ResultSet rsProgress = dbCon.getQuery(queryGetProgress);
            while (rsProgress.next()) {
                progressPerContentItem.put(contentItem, rsProgress.getInt("Progress"));
            }
            dbCon.CloseResultSet();
        }

        return progressPerContentItem;
    }

    public void updateProgress(Registration registration, ContentItem contentItem, int Progress) {
        String queryUpdateProgress = "UPDATE Viewing SET Progress = " + Progress + " WHERE StudentEmail = '" + registration.getStudentEmail() + "' AND ContenID = " + contentItem.getId() + " ";
        dbCon.setQuery(queryUpdateProgress);
    }

}
