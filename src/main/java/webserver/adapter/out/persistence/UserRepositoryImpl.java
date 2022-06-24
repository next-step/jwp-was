package webserver.adapter.out.persistence;

import webserver.domain.user.User;
import webserver.domain.user.UserRepository;

import java.util.Collection;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User findUserById(String userId) {
        return DataBase.findUserById(userId);
    }

    @Override
    public void addUser(User user) {
        DataBase.addUser(user);
    }

    @Override
    public Collection<User> findAll() {
        return DataBase.findAll();
    }
}
