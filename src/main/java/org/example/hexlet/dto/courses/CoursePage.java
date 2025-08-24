package org.example.hexlet.dto.courses;

import org.example.hexlet.dto.BasePage;
import org.example.hexlet.model.Course;

public class CoursePage extends BasePage {
    private Course course;

    public CoursePage(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
