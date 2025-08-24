package org.example.hexlet.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.hexlet.NamedRoutes;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.repository.CourseRepository;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class CoursesController {
    public static void index(Context ctx) {
        String term = ctx.queryParam("term");
        List<Course> courses = CourseRepository.getInstance().search(term);
        var header = term != null ? "Search Results" : "All Courses";
        var page = new CoursesPage(courses, header, term);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("courses/index.jte", model("page", page));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = CourseRepository.getInstance().find(id)
                .orElseThrow(() -> new NotFoundResponse("Course not found"));
        var page = new CoursePage(course);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("courses/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        ctx.render("courses/build.jte");
    }

    public static void create(Context ctx) {
        String name = ctx.formParam("name");
        String description = ctx.formParam("description");

        if (name == null || name.trim().isEmpty()) {
            ctx.sessionAttribute("flash", "Course name cannot be empty!");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.coursesPath());
            return;
        }

        Course course = new Course(name, description);
        CourseRepository.getInstance().save(course);

        ctx.sessionAttribute("flash", "Course has been created successfully!");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect(NamedRoutes.coursesPath());
    }

    public static void edit(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Course course = CourseRepository.getInstance().find(id)
                .orElseThrow(() -> new NotFoundResponse("Course not found"));
        CoursePage page = new CoursePage(course);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("courses/edit.jte", model("page", page));
    }

    public static void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        String name = ctx.formParam("name");
        String description = ctx.formParam("description");

        if (name == null || name.trim().isEmpty()) {
            ctx.sessionAttribute("flash", "Course name cannot be empty!");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect("/courses/" + id + "/edit");
            return;
        }

        Course course = CourseRepository.getInstance().find(id)
                .orElseThrow(() -> new NotFoundResponse("Course not found"));
        course.setName(name);
        course.setDescription(description);

        ctx.sessionAttribute("flash", "Course has been updated successfully!");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect("/courses/" + id);
    }

    public static void delete(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        CourseRepository.getInstance().delete(id);

        ctx.sessionAttribute("flash", "Course has been deleted successfully!");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect(NamedRoutes.coursesPath());
    }
}
