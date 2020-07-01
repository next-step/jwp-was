package model;

import http.RequestBody;

import java.util.Map;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User(final RequestBody requestBody) {
        Map<String, String> bodys = requestBody.getRequestBody();
        this.userId = bodys.getOrDefault("userId", "");
        this.password = bodys.getOrDefault("password", "");
        this.name = bodys.getOrDefault("name", "");
        this.email = bodys.getOrDefault("email", "");
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

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
