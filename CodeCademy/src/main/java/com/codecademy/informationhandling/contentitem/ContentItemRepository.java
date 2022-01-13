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

    public void addContentItemsToCourse(Course course, ArrayList<ContentItem> contentItems) {
        for (ContentItem contentItem : contentItems) {
            String query = "UPDATE ContentItem " +
                    "SET CourseName = '" + course.getName() + "' " +
                    "WHERE ContentID = '" + contentItem.getId() + "'";
            dbCon.setQuery(query);
        }
    }

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

}
