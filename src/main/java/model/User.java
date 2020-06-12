package model;

import http.HttpRequest;

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

    public static User ofRequest(HttpRequest request) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");
        final String name = request.getParameter("name");
        final String email = request.getParameter("email");

        return new User(userId, password, name, email);
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

    public boolean login(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
