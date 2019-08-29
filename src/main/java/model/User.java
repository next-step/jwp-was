package model;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {}

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

    public User update(User user) {
        this.userId = user.userId;
        this.email = user.email;
        this.name = user.name;
        this.password = user.password;
        return this;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
