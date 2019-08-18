package model;

import webserver.http.request.HttpRequestParams;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    private User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User of(HttpRequestParams reqParams) {
        return new User(reqParams.findByKey("userId")
                , reqParams.findByKey("password")
                , reqParams.findByKey("name")
                , reqParams.findByKey("email"));
    }

    public static User empty() {
        return new User();
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

    public boolean isOwner(HttpRequestParams requestParams) {
        if (isEmpty()) {
            return false;
        }

        String userId = requestParams.findByKey("userId");
        String password = requestParams.findByKey("password");

        return this.userId.equals(userId) && this.password.equals(password);
    }

    private boolean isEmpty() {
        return userId == null && password == null;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
