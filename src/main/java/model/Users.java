package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class Users {

    private List<User> users;

    public Users(List<User> users) {
        this.users = users;
    }

    public static Users ofCollection(Collection<User> users) {
        return new Users(new ArrayList<>(users));
    }

    public List<User> getUsers() {
        return users;
    }
}
