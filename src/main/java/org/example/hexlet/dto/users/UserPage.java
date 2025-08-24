package org.example.hexlet.dto.users;

import org.example.hexlet.dto.BasePage;
import org.example.hexlet.model.User;

public class UserPage extends BasePage {
    private User user;

    public UserPage(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
