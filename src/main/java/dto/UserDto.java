package dto;

import model.User;

public class UserDto {

    private int index;
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserDto(int index, User user) {
        this.index = index;
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public int getIndex() {
        return index;
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
}
