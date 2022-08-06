package model;

import webserver.http.model.QueryStrings;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(QueryStrings queryStrings) {
        this(queryStrings.queryStringValue("userId"), queryStrings.queryStringValue("password"),
                queryStrings.queryStringValue("name"), queryStrings.queryStringValue("email"));
    }

    public User(String userId, String password, String name, String email) {
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

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
