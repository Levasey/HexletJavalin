package org.example.hexlet.repository;

import org.example.hexlet.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UserRepository {
    private static AtomicLong idCounter = new AtomicLong(0);
    private static List<User> users = new ArrayList<>();

    public static void save(User user) {
        // Если у пользователя уже есть ID - это обновление
        if (user.getId() == null) {
            user.setId(idCounter.incrementAndGet());
            users.add(user);
        } else {
            // Обновление существующего пользователя
            Optional<User> existingUser = find(user.getId());
            if (existingUser.isPresent()) {
                User oldUser = existingUser.get();
                oldUser.setName(user.getName());
                oldUser.setEmail(user.getEmail());
                oldUser.setPassword(user.getPassword());
            } else {
                users.add(user);
            }
        }
    }

    public static List<User> search(String term) {
        if (term == null || term.isEmpty()) {
            return users;
        }
        return users.stream()
                .filter(user -> user.getName().toLowerCase().contains(term.toLowerCase()) ||
                        user.getEmail().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static Optional<User> find(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findAny();
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void delete(Long id) {
        users.remove(find(id));
    }
}
