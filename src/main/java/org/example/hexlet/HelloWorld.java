package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;

import java.util.List;

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

        // Название параметров мы выбрали произвольно
        app.get("/users/{id}/post/{postId}", ctx -> {
            var usersId = ctx.pathParam("id");
            var postId =  ctx.pathParam("postId");
            ctx.result("Users ID: " + usersId + " Post ID: " + postId);
        });

        List<Course> courses = List.of(
                new Course(1L,  "Java for Beginners", "Learn Java from scratch"),
                new Course(2L, "Advanced Java", "Deep dive into Java advanced topics")
        );

        app.get("/courses", ctx -> {
            String header = "Курсы по программированию";
            CoursesPage page = new CoursesPage(courses, header);
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

        app.start(7070); // Стартуем веб-сервер
    }
}
