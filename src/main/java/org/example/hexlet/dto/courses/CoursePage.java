package org.example.hexlet.dto.courses;

import org.example.hexlet.model.Course;

import lombok.Getter;

@Getter
public class CoursePage {
    private Course course;

    public CoursePage(Course course) {
        this.course = course;
    }
}
