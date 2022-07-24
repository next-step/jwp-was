package model;

public class User {
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_EMAIL = "email";
    public static final String DEFAULT_VALUE = "";

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

    private void validate(String userId, String password, String name, String email) {
        if (userId.isEmpty()) {
            throw new UserException("userId가 빈 값 입니다.");
        }
        if (password.isEmpty()) {
            throw new UserException("password가 빈 값 입니다.");
        }
        if (name.isEmpty()) {
            throw new UserException("name이 빈 값 입니다.");
        }
        if (email.isEmpty()) {
            throw new UserException("email이 빈 값 입니다.");
        }
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
