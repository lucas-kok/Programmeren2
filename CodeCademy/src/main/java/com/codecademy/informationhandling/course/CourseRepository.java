package com.codecademy.informationhandling.course;

import com.codecademy.informationhandling.contentitem.ContentItem;
import com.codecademy.informationhandling.databaseconnection.DatabaseConnection;
import com.codecademy.informationhandling.InformationFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseRepository {

    private final DatabaseConnection dbCon = new DatabaseConnection();
    private final InformationFormatter informationFormatter;

    public CourseRepository() {
        informationFormatter = new InformationFormatter();
    }

    // Function that creates a new Course
    public void createCourse(Course course) {
        informationFormatter.formatCourse(course);

        String queryCreateCourse = "INSERT INTO Course VALUES (" +
                "       '" + course.getName() + "'" +
                "       ,'" + course.getSubject() + "'" +
                "       ,'" + course.getIntroductionText() + "'" +
                "       ,'" + course.getLevel() + "')";
        dbCon.setQuery(queryCreateCourse);

        for (String courseName : course.getRelatedCourses().split(",")) {
            if (!courseName.isBlank()) return;

            String queryCreateRelatedCourse = "INSERT INTO RelatedCourses VALUES('" + course.getName() + "', '" + courseName.strip() + "')";
            dbCon.setQuery(queryCreateRelatedCourse);
        }
    }

    // Function that returns all Courses in the Database
    public Map<String, Course> getAllCourses() throws SQLException {
        HashMap<String, Course> relatedCourses = new HashMap<>();

        String query = "SELECT * FROM Course";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            String courseName = rs.getString("Coursename");
            String subject = rs.getString("Subject");
            String introductionText = rs.getString("IntroductionText");
            String level = rs.getString("Level");

            relatedCourses.put(courseName, new Course(courseName, subject, introductionText, level, ""));
        }
        dbCon.CloseResultSet();

        for (Course course : relatedCourses.values()) {
            StringBuilder relatedCoursesString = new StringBuilder();

            String getRelatedCoursesQuery = "SELECT CourseTwoName FROM RelatedCourses WHERE CourseOneName = '" + course.getName() + "'";
            ResultSet rsRelatedCourses = dbCon.getQuery(getRelatedCoursesQuery);

            while (rsRelatedCourses.next()) {
                relatedCoursesString.append(rsRelatedCourses.getString("CourseTwoName")).append(", ");
            }

            if (relatedCoursesString.length() != 0) {
                relatedCoursesString.replace(relatedCoursesString.length() - 2, relatedCoursesString.length(), "");
            }

            course.setRelatedCourses(relatedCoursesString.toString());
        }
        dbCon.CloseResultSet();

        return relatedCourses;
    }

    // Function that deletes a given Course
    public void deleteCourse(Course course) {
        String query = "DELETE FROM RelatedCourses " +
                "                   WHERE CourseOneName = '" + course.getName() + "' OR CourseTwoName = '" + course.getName() + "'" +
                "                   DELETE FROM Course " +
                "                   WHERE Coursename = '" + course.getName() + "'";
        dbCon.setQuery(query);
    }

    // Function that updates a given Course
    public void updateCourse(Course selectedCourse, String name, String subject, String introductionText, String level, String relatedCourses) {
        String oldCourseName = selectedCourse.getName();

        selectedCourse.setName(name);
        selectedCourse.setSubject(subject);
        selectedCourse.setIntroductionText(introductionText);
        selectedCourse.setLevel(level);
        selectedCourse.setRelatedCourses(relatedCourses);

        String query = "UPDATE RelatedCourses " +
                "                   SET CourseTwoName = '" + selectedCourse.getName().strip() + "'" +
                "                   WHERE CourseTwoName = '" + oldCourseName + "'" +
                "                   UPDATE Course " +
                "                   SET Coursename = '" + selectedCourse.getName().strip() + "'" +
                "                   , Subject = '" + selectedCourse.getSubject() + "'" +
                "                   , IntroductionText = '" + selectedCourse.getIntroductionText() + "'" +
                "                   ,Level = '" + selectedCourse.getLevel() + "'" +
                "                   WHERE Coursename = '" + oldCourseName + "'" +
                "                   DELETE FROM RelatedCourses \n" +
                "                   WHERE CourseOneName = '" + selectedCourse.getName().strip() + "'";
        dbCon.setQuery(query);

        for (String courseName : selectedCourse.getRelatedCourses().split(",")) {
            if (!courseName.isBlank()) return;

            String queryCreateRelatedCourse = "INSERT INTO RelatedCourses VALUES('" + name.strip() + "', '" + courseName.strip() + "')";
            dbCon.setQuery(queryCreateRelatedCourse);
        }
    }

    // Function that returns a Map of Content Items and their average progression
    public HashMap<ContentItem, Integer> getAverageProgressPerContentItem(Course course) throws SQLException {
        HashMap<ContentItem, Integer> averageProgressPerContentItem = new HashMap<>();
        ArrayList<ContentItem> contentItems = new ArrayList<>();

        String query = "SELECT * FROM ContentItem WHERE CourseName = '" + course.getName() + "'";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            contentItems.add(new ContentItem(rs.getInt("ContentID"), rs.getString("Title"), rs.getDate("PublicationDate").toString()));
        }
        dbCon.CloseResultSet();

        for (ContentItem contentItem : contentItems) {
            String queryGetAllProgress = "SELECT Progress FROM Viewing WHERE ContenID = " + contentItem.getId() + "";
            ResultSet rsAllProgress = dbCon.getQuery(queryGetAllProgress);

            int studentsCounter = 0;
            int totalProgress = 0;

            while (rsAllProgress.next()) {
                studentsCounter++;
                totalProgress += rsAllProgress.getInt("Progress");
            }
            dbCon.CloseResultSet();

            // Can't divide by 0!
            if (studentsCounter != 0) {
                averageProgressPerContentItem.put(contentItem, (totalProgress / studentsCounter));
            } else {
                averageProgressPerContentItem.put(contentItem, 0);
            }
        }

        return averageProgressPerContentItem;
    }

    // Function that returns the number of Certificates for a given Course
    public int getAmountOfCertificatesOfCourse (Course course) throws SQLException {
        int certificatesCount = 0;

        String query = "SELECT COUNT(CertificateID) as 'amount' FROM Certificate WHERE RegisterID IN (SELECT RegisterID FROM Register WHERE CourseName = '" + course.getName() + "')";
        ResultSet rs = dbCon.getQuery(query);

        while (rs.next()) {
            certificatesCount = rs.getInt("amount");
        }

        return certificatesCount;
    }
}
