package model;

public class LoginRequest {

    private final String userId;
    private final String password;

    public LoginRequest(String userId, String password) {
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
}
