package com.codecademy.informationhandling.Course;

public class Course {

    private String name;
    private String subject;
    private String introductionText;
    private String level;
    private String relatedCourses;

    public Course(String name, String subject, String introductionText, String level, String relatedCourses) {
        this.name = name;
        this.subject = subject;
        this.introductionText = introductionText;
        this.level = level;
        this.relatedCourses = relatedCourses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getRelatedCourses() {
        return relatedCourses;
    }

    public void setRelatedCourses(String relatedCourses) {
        this.relatedCourses = relatedCourses;
    }
}
