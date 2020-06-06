package model;

import http.request.QueryString;

import java.util.HashMap;
import java.util.Objects;

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

    public User(QueryString queryString) {
        HashMap<String, String> param = queryString.getParam();
        this.userId = getOfDefault(param, "userId");
        this.email = getOfDefault(param, "email");
        this.name = getOfDefault(param, "name");
        this.password = getOfDefault(param, "password");
    }

    private String getOfDefault(HashMap<String, String> param, String userId) {
        return param.getOrDefault(userId, "");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

}
