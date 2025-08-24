package org.example.hexlet.repository;

import org.example.hexlet.Database;
import org.example.hexlet.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository extends BaseRepository<Course> {
    private static final CourseRepository instance = new CourseRepository();

    public static CourseRepository getInstance() {
        return instance;
    }

    @Override
    protected String getTableName() {
        return "courses";
    }

    @Override
    protected Course mapToEntity(ResultSet rs) throws SQLException {
        return new Course(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }

    @Override
    protected void setStatementParameters(PreparedStatement stmt, Course course) throws SQLException {
        stmt.setString(1, course.getName());
        stmt.setString(2, course.getDescription());
    }

    @Override
    public void save(Course course) {
        if (course.getId() == null) {
            insert(course);
        } else {
            update(course);
        }
    }

    private void insert(Course course) {
        String sql = "INSERT INTO courses (name, description) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setStatementParameters(stmt, course);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                course.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting course", e);
        }
    }

    private void update(Course course) {
        String sql = "UPDATE courses SET name = ?, description = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setStatementParameters(stmt, course);
            stmt.setLong(3, course.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating course", e);
        }
    }

    public List<Course> search(String term) {
        if (term == null || term.isEmpty()) {
            return findAll();
        }

        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE name ILIKE ? OR description ILIKE ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + term + "%");
            stmt.setString(2, "%" + term + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(mapToEntity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching courses", e);
        }

        return courses;
    }
}