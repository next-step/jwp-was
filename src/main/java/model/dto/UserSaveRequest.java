package model.dto;

import model.User;
import webserver.domain.EntitySupplier;
import webserver.domain.RequestBody;

public class UserSaveRequest implements EntitySupplier<User> {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public UserSaveRequest(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserSaveRequest from(RequestBody body) {
        return new UserSaveRequest(
                body.getAttribute("userId"),
                body.getAttribute("password"),
                body.getAttribute("name"),
                body.getAttribute("email")
        );
    }

    @Override
    public User supply() {
        return new User(userId, password, name, email);
    }
}
