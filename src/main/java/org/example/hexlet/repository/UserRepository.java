package org.example.hexlet.repository;

import org.example.hexlet.Database;
import org.example.hexlet.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository<User> {
    private static final UserRepository instance = new UserRepository();

    public static UserRepository getInstance() {
        return instance;
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected User mapToEntity(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password")
        );
    }

    @Override
    protected void setStatementParameters(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPassword());
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            insert(user);
        } else {
            update(user);
        }
    }

    private void insert(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setStatementParameters(stmt, user);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting user", e);
        }
    }

    private void update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setStatementParameters(stmt, user);
            stmt.setLong(4, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public List<User> search(String term) {
        if (term == null || term.isEmpty()) {
            return findAll();
        }

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE name ILIKE ? OR email ILIKE ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + term + "%");
            stmt.setString(2, "%" + term + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(mapToEntity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching users", e);
        }

        return users;
    }
}
