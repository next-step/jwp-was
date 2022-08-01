package model;

public class LoginUser {
    private final String userId;
    private final String password;

    public LoginUser(String userId, String password) {
        validate(userId, password);
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    private void validate(String userId, String password) {
        if (userId.isEmpty()) {
            throw new UserException("userId가 빈 값 입니다.");
        }
        if (password.isEmpty()) {
            throw new UserException("password가 빈 값 입니다.");
        }
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password;
    }
}
