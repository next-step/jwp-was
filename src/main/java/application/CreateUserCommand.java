package application;

import model.User;

public class CreateUserCommand {
    private String userId;
    private String password;
    private String name;
    private String email;

    public CreateUserCommand(String userId, String password, String name, String email) {
        this.userId = userId;
        validateUserId();

        this.password = password;
        this.name = name;
        this.email = email;
    }

    private void validateUserId() {
        if (isNotExistsUser()) {
            throw new IllegalArgumentException("유효하지 않은 유저 아이디입니다");
        }
    }

    public boolean isNotExistsUser() {
        return userId == null || userId.isEmpty();
    }

    public User toUser() {
        return new User(userId, password, name, email);
    }
}
