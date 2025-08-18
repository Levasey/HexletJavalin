package org.example.hexlet.repository;

import org.example.hexlet.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static Long PEOPLE_COUNT = 0L;
    private List<User> users;

    {
        users = new ArrayList<User>();

        users.add(new User(++PEOPLE_COUNT, "Tom", "tom@mail.com", "1"));
        users.add(new User(++PEOPLE_COUNT, "Bob", "bob@mail.com", "2"));
        users.add(new User(++PEOPLE_COUNT, "Mike", "mike@mail.com", "3"));
        users.add(new User(++PEOPLE_COUNT, "Katy", "katy@mail.com", "4"));
    }

    public List<User> index() {
        return users;
    }

    public User show(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findAny().orElse(null);
    }

    public void save(User user) {
        user.setId(++PEOPLE_COUNT);
        users.add(user);
    }
}

