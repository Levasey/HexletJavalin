package org.example.hexlet.repository;

import java.util.ArrayList;
import java.util.List;

import org.example.hexlet.model.Course;

public class CourseRepository {
    // Тип зависит от того, с какой сущностью идет работа в упражнении
    private static Long COURSE_COUNT = 0L;
    private static List<Course> courses;

    // Initial list of courses
    {
        courses = new ArrayList<Course>();

        courses.add(new Course(++COURSE_COUNT, "Java for Beginners", "Learn Java from scratch"));
        courses.add(new Course(++COURSE_COUNT, "Advanced Java", "Deep dive into Java advanced topics"));
        courses.add(new Course(++COURSE_COUNT, "Web Development", "Learn HTML, CSS and JavaScript"));
        courses.add(new Course(++COURSE_COUNT, "Database Fundamentals", "SQL and relational databases"));
    }

    public List<Course> index() {
        return courses;
    }

    public Course show(Long id) {
        return courses.stream().filter(course -> course.getId().equals(id)).findAny().orElse(null);
    }

    public void save(Course course) {
        course.setId(++COURSE_COUNT);
        courses.add(course);
    }
}
