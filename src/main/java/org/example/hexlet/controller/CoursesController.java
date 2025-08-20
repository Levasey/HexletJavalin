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
        List<Course> courses = CourseRepository.search(term);
        var header = term != null ? "Search Results" : "All Courses";
        var page = new CoursesPage(courses, header, term);
        ctx.render("courses/index.jte", model("page", page));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Course not found"));
        var page = new CoursePage(course);
        ctx.render("courses/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        ctx.render("courses/build.jte");
    }

    public static void create(Context ctx) {
        String name = ctx.formParam("name");
        String description = ctx.formParam("description");
        Course course = new Course(name, description);
        CourseRepository.save(course);
        ctx.redirect(NamedRoutes.coursesPath());
    }

    public static void edit(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Course course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Course not found"));
        CoursePage page = new CoursePage(course);
        ctx.render("courses/edit.jte", model("page", page));
    }

    public static void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        String name = ctx.formParam("name");
        String description = ctx.formParam("description");

        Course course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Course not found"));

        course.setName(name);
        course.setDescription(description);
        CourseRepository.save(course);
        ctx.redirect(NamedRoutes.coursesPath());
    }

    public static void delete(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        CourseRepository.delete(id);
        ctx.redirect(NamedRoutes.coursesPath());
    }
}
