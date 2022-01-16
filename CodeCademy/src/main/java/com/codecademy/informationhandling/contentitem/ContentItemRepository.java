package com.codecademy.informationhandling.contentitem;

import com.codecademy.informationhandling.course.Course;
import com.codecademy.informationhandling.databaseconnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContentItemRepository {

    private final DatabaseConnection dbCon;

    public ContentItemRepository() {
        dbCon = new DatabaseConnection();
    }

    // Function that will add an unused Content Item to a Course
    public void addContentItemsToCourse(Course course, ArrayList<ContentItem> contentItems) {
        for (ContentItem contentItem : contentItems) {
            String query = "UPDATE ContentItem " +
                    "SET CourseName = '" + course.getName() + "' " +
                    "WHERE ContentID = '" + contentItem.getId() + "'";
            dbCon.setQuery(query);
        }
    }

    // Function that returns all Content Items not linked to a Course
    public ArrayList<ContentItem> getUnusedContentItems() throws SQLException {
        ArrayList<ContentItem> contentItems = new ArrayList<>();

        String query = "SELECT * FROM ContentItem WHERE CourseName IS NULL";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            contentItems.add(new ContentItem(rs.getInt("ContentiD"), rs.getString("Title"), rs.getString("PublicationDate")));
        }
        dbCon.CloseResultSet();

        return contentItems;
    }

    // Function that returns a single Content Item based on a given ID
    public ContentItem getContentItem(String id) throws SQLException {
        String query = "SELECT * FROM ContentItem WHERE ContentiD = '" + id + "'";
        ResultSet rs = dbCon.getQuery(query);

        ContentItem newContentItem;
        if (rs.next()) {
            newContentItem = new ContentItem(rs.getInt("ContentiD"), rs.getString("Title"), rs.getString("PublicationDate"));
            return newContentItem;
        }
        dbCon.CloseResultSet();

        return null;
    }
}
