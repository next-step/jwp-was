package application;

import model.User;

public class CreateUserCommand {
    private String userId;
    private String password;
    private String name;
    private String email;

    public CreateUserCommand(String userId, String password, String name, String email) {
        validateUserId(userId);

        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private void validateUserId(String userId) {
        if (isNotExistsUser(userId)) {
            throw new IllegalArgumentException("유효하지 않은 유저 아이디입니다");
        }
    }

    private boolean isNotExistsUser(String userId) {
        return userId == null || userId.isEmpty();
    }

    public User convertToUser() {
        return new User(userId, password, name, email);
    }
}
