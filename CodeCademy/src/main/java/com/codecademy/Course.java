package com.codecademy;

import java.util.ArrayList;

public class Course {
    private String name;
    private String subject;
    private String introductionText;
    private String level;
    private ArrayList<Course> relatedCourses;

    public Course(String name, String subject, String introductionText, String level, ArrayList<Course> relatedCourses) {
        this.name = name;
        this.subject = subject;
        this.introductionText = introductionText;
        this.level = level;
        this.relatedCourses = relatedCourses;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIntroductionText() {
        return introductionText;
    }

    public void setIntroductionText(String introductionText) {
        this.introductionText = introductionText;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ArrayList<Course> getRelatedCourses() {
        return relatedCourses;
    }

    public void setRelatedCourses(ArrayList<Course> relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

    public String getRelatedCoursesAsString() {
        ArrayList<Course> relatedCourses = getRelatedCourses();
        if (relatedCourses == null) return "";
        if (relatedCourses.size() == 0) return "";

        StringBuilder relatedCoursesString = new StringBuilder();
        for (Course course : relatedCourses) {
            relatedCoursesString.append(", ").append(course.getName());
        }

        return relatedCoursesString.toString();
    }
}
