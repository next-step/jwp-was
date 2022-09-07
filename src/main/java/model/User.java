package model;

import exception.ValidateException;

public class User {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        validate(userId, password, name, email);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private void validate(String... arguments) {
        for (String argument : arguments) {
            if (argument == null || argument.trim().length() == 0) {
                throw new ValidateException(argument);
            }
        }
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
