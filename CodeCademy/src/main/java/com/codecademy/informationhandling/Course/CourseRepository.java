package com.codecademy.informationhandling.Course;

import com.codecademy.informationhandling.ContentItem.ContentItem;
import com.codecademy.informationhandling.Databaseconnection.DatabaseConnection;
import com.codecademy.informationhandling.InformationFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CourseRepository {

    private DatabaseConnection dbCon = new DatabaseConnection();
    private final InformationFormatter informationFormatter;

    public CourseRepository() {
        informationFormatter = new InformationFormatter();
    }

    public void createCourse(Course course) {
        informationFormatter.formatCourse(course);
        String queryCreateCourse = "INSERT INTO Course VALUES (" +
                "       '" + course.getName() + "'" +
                "       ,'" + course.getSubject() + "'" +
                "       ,'" + course.getIntroductionText() + "'" +
                "       ,'" + course.getLevel() + "')";
        dbCon.setQuery(queryCreateCourse);
        for (String courseName : course.getRelatedCoursesAsString().split(",")) {
            if (!courseName.isEmpty()) {
                String queryCreateRelatedCourse = "INSERT INTO RelatedCourses VALUES('" + course.getName() + "', '" + courseName.strip() + "')";
                dbCon.setQuery(queryCreateRelatedCourse);
            }
        }
    }

    public Map<String, Course> getAllCourses() throws SQLException {
        HashMap<String, Course> courseList = new HashMap<>();
        String query = "SELECT * FROM Course";
        ResultSet rs = dbCon.getQuery(query);
        while (rs.next()) {
            String courseName = rs.getString("Coursename");
            String Subject = rs.getString("Subject");
            String IntroductionText = rs.getString("IntroductionText");
            String level = rs.getString("Level");
            courseList.put(courseName, new Course(courseName, Subject, IntroductionText, level, ""));
        }
        dbCon.CloseResultSet();
        for (Course course : courseList.values()) {
            StringBuilder relatedCourses = new StringBuilder();
            String getRealtedcoursesQuery = "SELECT CourseTwoName FROM RelatedCourses WHERE CourseOneName = '" + course.getName() + "'";
            ResultSet rsRelatedCourses = dbCon.getQuery(getRealtedcoursesQuery);
            while (rsRelatedCourses.next()) {
                relatedCourses.append(rsRelatedCourses.getString("CourseTwoName") + ", ");
            }
            if (relatedCourses.length() != 0)
                relatedCourses.replace(relatedCourses.length() - 2, relatedCourses.length(), "");
            course.setRelatedCourses(relatedCourses.toString());
        }
        dbCon.CloseResultSet();
        return courseList;
    }

    public void deleteCourse(Course course) {
        String queryDeleteCourse = "DELETE FROM RelatedCourses " +
                "                   WHERE CourseOneName = '" + course.getName() + "' OR CourseTwoName = '" + course.getName() + "'" +
                "                   DELETE FROM Course " +
                "                   WHERE Coursename = '" + course.getName() + "'";
        dbCon.setQuery(queryDeleteCourse);
    }

    public void updateCourse(Course selectedCourse, String name, String subject, String introductionText, String level, String relatedCourses) {
        String queryUpdateCourse = "UPDATE RelatedCourses " +
                "                   SET CourseTwoName = '" + name.strip() + "'" +
                "                   WHERE CourseTwoName = '" + selectedCourse.getName() + "'" +
                "                   UPDATE Course " +
                "                   SET Coursename = '" + name.strip() + "'" +
                "                   , Subject = '" + subject + "'" +
                "                   , IntroductionText = '" + introductionText + "'" +
                "                   ,Level = '" + level + "'" +
                "                   WHERE Coursename = '" + selectedCourse.getName() + "'" +
                "                   DELETE FROM RelatedCourses \n" +
                "                   WHERE CourseOneName = '" + name.strip() + "'";
        dbCon.setQuery(queryUpdateCourse);
        for (String courseName : relatedCourses.split(",")) {
            if (!courseName.isEmpty()) {
                String queryCreateRelatedCourse = "INSERT INTO RelatedCourses VALUES('" + name.strip() + "', '" + courseName.strip() + "')";
                dbCon.setQuery(queryCreateRelatedCourse);
            }
        }
    }

    //get average progress for this courses contenitems
    public HashMap<ContentItem, Integer> getAverageProgressPerContentItem() {
        return  new HashMap<>();
    }

    //get how many certificates per course
    public int getAmountOfCertificates (Course course) {
        return 0;
    }

}
