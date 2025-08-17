package org.example.hexlet.dto.courses;

import java.util.List;

import org.example.hexlet.model.Course;

public class CoursesPage {
    private List<Course> courses;
    private String header;
    private String term;

    public CoursesPage(List<Course> courses, String header, String term) {
        this.courses = courses;
        this.header = header;
        this.term = term;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public String getHeader() {
        return header;
    }

    public String getTerm() {
        return term;
    }
}
