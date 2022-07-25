package model.dto;

import model.User;

public class UserResponse {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;


    public UserResponse(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail()
        );
    }
}
