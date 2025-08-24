package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.UsersController;

public class HelloWorld {
    public static void main(String[] args) {
        // Создаем приложение
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/courses", CoursesController::index);
        app.get("/courses/build", CoursesController::build);
        app.get("/courses/{id}", CoursesController::show);
        app.post("/courses", CoursesController::create);
        app.get("/courses/{id}/edit", CoursesController::edit);
        app.patch("/courses/{id}", CoursesController::update);
        app.post("/courses/{id}", CoursesController::update);
        app.delete("/courses/{id}", CoursesController::delete);
        app.post("/courses/{id}/delete", CoursesController::delete);

        app.get("/users", UsersController::index);
        app.get("/users/build", UsersController::build);
        app.get("/users/{id}", UsersController::show);
        app.post("/users", UsersController::create);
        app.get("/users/{id}/edit", UsersController::edit);
        app.patch("/users/{id}", UsersController::update);
        app.post("/users/{id}", UsersController::update);
        app.delete("/users/{id}", UsersController::destroy);
        app.post("/users/{id}/delete", UsersController::destroy);

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Database.close();
            System.out.println("Database connections closed");
        }));

        app.start(7070); // Стартуем веб-сервер
    }
}
