package model;

import http.QueryStrings;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User of(QueryStrings queryStrings) throws UnsupportedEncodingException {
        String userId = URLDecoder.decode(queryStrings.getQueryStrings().get("userId"), "UTF-8");
        String password = URLDecoder.decode(queryStrings.getQueryStrings().get("password"), "UTF-8");
        String name = URLDecoder.decode(queryStrings.getQueryStrings().get("name"), "UTF-8");
        String email = URLDecoder.decode(queryStrings.getQueryStrings().get("email"), "UTF-8");

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

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }
}
