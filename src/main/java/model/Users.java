package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Users {
    private final List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

    public void addUseraAll(Collection<User> users) {
        users.stream()
                .forEach(user -> addUser(user));
    }

    public List<User> getUsers() {
        return userList;
    }
}
