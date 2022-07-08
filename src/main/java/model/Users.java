package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Users {

    private final List<User> users;

    private Users(List<User> users) {
        this.users = users;
    }

    public static Users from(Collection<User> users) {
        return new Users(new ArrayList<>(users));
    }

    public List<User> toList() {
        return List.copyOf(users);
    }
}
