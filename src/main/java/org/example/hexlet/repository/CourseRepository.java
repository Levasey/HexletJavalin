package org.example.hexlet.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.example.hexlet.model.Course;

public class CourseRepository {
    private static AtomicLong idCounter = new AtomicLong(0);
    private static List<Course> entities = new ArrayList<>();

    public static void save(Course course) {
        course.setId(idCounter.incrementAndGet());
        entities.add(course);
    }

    public static List<Course> search(String term) {
        if (term == null || term.isEmpty()) {
            return entities;
        }
        return entities.stream()
                .filter(course -> course.getName().toLowerCase().contains(term.toLowerCase()) ||
                        course.getDescription().toLowerCase().contains(term.toLowerCase()))
                .toList();
    }

    public static Optional<Course> find(Long id) {
        return entities.stream()
                .filter(course -> course.getId().equals(id))
                .findAny();
    }

    public static List<Course> getEntities() {
        return entities;
    }

    public static void delete(Long id) {
        entities.remove(find(id));
    }
}
