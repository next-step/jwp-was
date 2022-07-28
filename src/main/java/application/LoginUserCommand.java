package application;

public class LoginUserCommand {
    private String userId;
    private String password;

    public LoginUserCommand(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
