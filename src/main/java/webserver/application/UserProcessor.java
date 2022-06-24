package webserver.application;

import webserver.domain.user.User;

import java.util.Collection;

public interface UserProcessor {

    boolean isValidUser(String userId, String password);

    void createUser(String userId, String password, String name, String email);

    Collection<User> getUsers();
}
