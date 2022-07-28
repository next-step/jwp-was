package application;

import db.DataBase;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class GetUsersService {

    public static List<User> getAllUsers() {
        return new ArrayList<>(DataBase.findAll());
    }
}
