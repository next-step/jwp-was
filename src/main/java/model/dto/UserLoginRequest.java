package model.dto;

import model.User;
import webserver.domain.EntitySupplier;
import webserver.domain.RequestBody;

public class UserLoginRequest implements EntitySupplier<User> {
    private final String userId;
    private final String password;

    public UserLoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static UserLoginRequest from(RequestBody requestBody) {
        return new UserLoginRequest(requestBody.getAttribute("userId"), requestBody.getAttribute("password"));
    }

    @Override
    public User supply() {
        return new User(userId, password, null, null);
    }
}
