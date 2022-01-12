package com.codecademy.informationhandling.Registration;

import com.codecademy.informationhandling.ContentItem.ContentItem;
import com.codecademy.informationhandling.Databaseconnection.DatabaseConnection;
import com.codecademy.informationhandling.Student.Student;

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

    public ArrayList<Registration> getAllRegistrations() {
        return new ArrayList<>();
    }

    public void updateRegistration(Student selectedStudent, String name, String email, String address, String postalCode, String city, String country, String gender, LocalDate birthday) {

    }

    public void deleteRegistration(Student selectedStudent) {

    }

    public HashMap<ContentItem, Integer> getProgressForRegistration(Registration registration) throws SQLException {
        HashMap<ContentItem, Integer> progressPerContentItem = new HashMap<>();
        ArrayList<ContentItem> contentItems = new ArrayList<>();
        String queryGetAllContentItems = "SELECT * FROM ContentItem WHERE CourseName = '" + registration.getCourseName() + "'";
        ResultSet rs = dbCon.getQuery(queryGetAllContentItems);
        while (rs.next()) {
            contentItems.add(new ContentItem(rs.getInt("ContentID"), rs.getString("Title"), rs.getString("PublicationDate'")));
        }
        dbCon.CloseResultSet();
        for (ContentItem contentItem : contentItems) {
            String queryGetProgress = "SELECT Progress FROM Viewing WHERE StudentEmail = '" + registration.getStudentEmail() + "' AND ContenID = '" + contentItem.getId() + "'";
            dbCon.getQuery(queryGetProgress);
            progressPerContentItem.put(contentItem, rs.getInt("Progress"));
            dbCon.CloseResultSet();
        }
        return progressPerContentItem;
    }

    public void updateProgress(Registration registration, ContentItem contentItem, int Progress) {
        String queryUpdateProgress = "UPDATE Viewing SET Progress = " + Progress + " WHERE StudentEmail = '" + registration.getStudentEmail() + "' AND ContenID = " + contentItem.getId() + " ";
        dbCon.setQuery(queryUpdateProgress);
    }

}
