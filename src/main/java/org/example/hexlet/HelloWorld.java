package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;

import java.util.List;
import java.util.stream.Collectors;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
    public static void main(String[] args) {
        // Создаем приложение
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        // Описываем, что загрузится по адресу /
        app.get("/hello", ctx -> {
            String name = ctx.queryParam("name"); // Извлекаем параметр name
            String greeting = (name != null && !name.isEmpty()) ? "Hello, " + name + "!" : "Hello, World!";
            ctx.result(greeting); // Возвращаем результат
        });

        app.get("/users/{id}/post/{postId}", ctx -> {
            var usersId = ctx.pathParam("id");
            var postId =  ctx.pathParam("postId");
            ctx.result("Users ID: " + usersId + " Post ID: " + postId);
        });

        // Initial list of courses
        List<Course> courses = List.of(
                new Course(1L, "Java for Beginners", "Learn Java from scratch"),
                new Course(2L, "Advanced Java", "Deep dive into Java advanced topics"),
                new Course(3L, "Web Development", "Learn HTML, CSS and JavaScript"),
                new Course(4L, "Database Fundamentals", "SQL and relational databases")
        );

        app.get("/courses", ctx -> {
            var term = ctx.queryParam("term");
            List<Course> filteredCourses = courses;
            if (term != null && !term.isEmpty()) {
                String lowerTerm = term.toLowerCase();
                filteredCourses = courses.stream()
                        .filter(c -> c.getName().toLowerCase().contains(lowerTerm) ||
                                c.getDescription().toLowerCase().contains(lowerTerm))
                        .collect(Collectors.toList());
            }
            String header = filteredCourses.isEmpty() ? "No courses found" : "Programming courses";
            CoursesPage page = new CoursesPage(filteredCourses, header, term);
            ctx.render("courses/index.jte", model("page", page));
        });

        app.get("/courses/{id}", ctx -> {
            String id = ctx.pathParam("id");
            Course course = courses.stream()
                    .filter(c -> c.getId().equals(Long.valueOf(id)))
                    .findFirst()
                    .orElse(null);
            if (course == null) {
                ctx.status(404);
                ctx.result("Курс не найден");
                return;
            }

            CoursePage page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        app.start(7070); // Стартуем веб-сервер
    }
}
