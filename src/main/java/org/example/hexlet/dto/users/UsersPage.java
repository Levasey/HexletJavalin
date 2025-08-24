package org.example.hexlet.dto.users;

import org.example.hexlet.dto.BasePage;
import org.example.hexlet.model.User;
import java.util.List;

public class UsersPage extends BasePage {
    private List<User> users;
    private String header;
    private String term;

    public UsersPage(List<User> users) {
        this.users = users;
    }

    public UsersPage(List<User> users, String header, String term) {
        this.users = users;
        this.header = header;
        this.term = term;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getHeader() {
        return header;
    }

    public String getTerm() {
        return term;
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public boolean isSearch() {
        return term != null && !term.isEmpty();
    }
}
