package com.example.sms.model;

public class Course {
    private int courseId;
    private String courseName;
    private int durationWeeks;

    public Course() {}

    public Course(int courseId, String courseName, int durationWeeks) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.durationWeeks = durationWeeks;
    }

    public Course(String courseName, int durationWeeks) {
        this.courseName = courseName;
        this.durationWeeks = durationWeeks;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(int durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", durationWeeks=" + durationWeeks +
                '}';
    }
}
