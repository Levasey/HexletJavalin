package org.example.hexlet.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.hexlet.NamedRoutes;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.UserRepository;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UsersController {
    public static void index(Context ctx) {
        String term = ctx.queryParam("term");
        List<User> users = UserRepository.getInstance().search(term);
        var header = term != null ? "Search Results" : "All Users";
        var page = new UsersPage(users, header, term);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("users/index.jte", model("page", page));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = UserRepository.getInstance().find(id)
                .orElseThrow(() -> new NotFoundResponse("User not found"));
        var page = new UserPage(user);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("users/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        ctx.render("users/build.jte");
    }

    public static void create(Context ctx) {
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String passwordConfirmation = ctx.formParam("passwordConfirmation");

        // Валидация
        if (name == null || name.trim().isEmpty()) {
            ctx.sessionAttribute("flash", "Name cannot be empty!");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.buildUserPath());
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            ctx.sessionAttribute("flash", "Email cannot be empty!");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.buildUserPath());
            return;
        }

        if (!password.equals(passwordConfirmation)) {
            ctx.sessionAttribute("flash", "Passwords do not match!");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.buildUserPath());
            return;
        }

        User user = new User(name, email, password);
        UserRepository.getInstance().save(user);

        ctx.sessionAttribute("flash", "User registered successfully!");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect(NamedRoutes.usersPath());
    }

    public static void edit(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        User user = UserRepository.getInstance().find(id)
                .orElseThrow(() -> new NotFoundResponse("User not found"));
        UserPage page = new UserPage(user);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("users/edit.jte", model("page", page));
    }

    public static void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        if (name == null || name.trim().isEmpty()) {
            ctx.sessionAttribute("flash", "Name cannot be empty!");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect("/users/" + id + "/edit");
            return;
        }

        User user = UserRepository.getInstance().find(id)
                .orElseThrow(() -> new NotFoundResponse("User not found"));

        user.setName(name);
        user.setEmail(email);
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        ctx.sessionAttribute("flash", "User updated successfully!");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect("/users/" + id);
    }

    public static void destroy(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        UserRepository.getInstance().delete(id);

        ctx.sessionAttribute("flash", "User deleted successfully!");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect(NamedRoutes.usersPath());
    }
}
