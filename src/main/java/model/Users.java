package model;

import java.util.Collection;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class Users {
    private Collection<User> users;

    public Users(Collection<User> users) {
        this.users = users;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
