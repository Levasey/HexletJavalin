package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.UserRepository;
import org.example.hexlet.repository.CourseRepository;

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
            var postId = ctx.pathParam("postId");
            ctx.result("Users ID: " + usersId + " Post ID: " + postId);
        });

        app.get("/courses", ctx -> {
            var term = ctx.queryParam("term");
            CourseRepository courseRepository = new CourseRepository();
            List<Course> filteredCourses = courseRepository.index();
            if (term != null && !term.isEmpty()) {
                String lowerTerm = term.toLowerCase();
                filteredCourses.stream()
                        .filter(c -> c.getName().toLowerCase().contains(lowerTerm) ||
                                c.getDescription().toLowerCase().contains(lowerTerm))
                        .collect(Collectors.toList());
            }
            String header = filteredCourses.isEmpty() ? "No courses found" : "Programming courses";
            CoursesPage page = new CoursesPage(filteredCourses, header, term);
            ctx.render("courses/index.jte", model("page", page));
        });

        app.get("/courses/build", ctx -> {
            ctx.render("courses/build.jte");
        });

        app.post("/courses", ctx -> {
            var name = ctx.formParam("name");
            var description = ctx.formParam("description");

            Course course = new Course(name, description);
            CourseRepository repo = new CourseRepository();
            repo.save(course);
            ctx.redirect("/courses");
        });

        app.get("/courses/{id}", ctx -> {
            String id = ctx.pathParam("id");
            CourseRepository repo = new CourseRepository();

            Course course = repo.show(Long.parseLong(id));
            if (course == null) {
                ctx.status(404);
                ctx.result("Курс не найден");
                return;
            }

            CoursePage page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get("/users/build", ctx -> {
            ctx.render("users/build.jte");
        });

        app.post("/users", ctx -> {
            var name = ctx.formParam("name").trim();
            var email = ctx.formParam("email").trim().toLowerCase();
            var password = ctx.formParam("password");
            var passwordConfirmation = ctx.formParam("passwordConfirmation");

            var user = new User(name, email, password);
            UserRepository userRepository = new UserRepository();
            userRepository.save(user);
            ctx.redirect("/");
        });

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        app.start(7070); // Стартуем веб-сервер
    }
}
