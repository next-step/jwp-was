package dto;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class Users {

    private List<UserDto> users = new ArrayList<>();

    public Users(List<User> items) {
        int index = 0;
        for (User user : items) {
            users.add(new UserDto(index, user));
            index++;
        }
    }

    public List<UserDto> getUsers() {
        return users;
    }
}
