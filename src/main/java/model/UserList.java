package model;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    List<User> userList = new ArrayList<>();

    public UserList(ArrayList<User> users) {
        this.userList = users;
    }


    public List<User> getUserList() {
        return userList;
    }
}
