package org.example.hexlet.repository;

import org.example.hexlet.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> {
    protected abstract String getTableName();
    protected abstract T mapToEntity(ResultSet rs) throws SQLException;
    protected abstract void setStatementParameters(PreparedStatement stmt, T entity) throws SQLException;

    public void save(T entity) {
        // Будет реализовано в дочерних классах
    }

    public Optional<T> find(Long id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapToEntity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding entity", e);
        }

        return Optional.empty();
    }

    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                entities.add(mapToEntity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all entities", e);
        }

        return entities;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting entity", e);
        }
    }

    public List<T> search(String field, String term) {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + field + " ILIKE ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + term + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                entities.add(mapToEntity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching entities", e);
        }

        return entities;
    }
}
