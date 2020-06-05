package model;

import http.HttpMethod;
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

    public static User init(final HttpRequest request) {
        if (request.getMethod() == HttpMethod.GET) {
            return new User(
                    request.getParameter("userId"),
                    request.getParameter("password"),
                    request.getParameter("name"),
                    request.getParameter("email")
            );
        }

        return new User(
                request.getBodyParameter("userId"),
                request.getBodyParameter("password"),
                request.getBodyParameter("name"),
                request.getBodyParameter("email")
        );
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
