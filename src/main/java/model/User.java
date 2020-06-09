package model;

import http.HttpRequest;

public class User {

    private static final String USER_ID_FIELD ="userId";
    private static final String PASSWORD_FIELD ="password";
    private static final String NAME_FIELD ="name";
    private static final String EMAIL_FIELD ="email";

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
        return new User(request.getParameter(USER_ID_FIELD), request.getParameter(PASSWORD_FIELD),
            request.getParameter(NAME_FIELD), request.getParameter(EMAIL_FIELD));
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

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
