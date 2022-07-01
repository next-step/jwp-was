package webserver.application;

import webserver.domain.user.User;
import webserver.domain.user.UserRepository;

import java.util.Collection;

public class UserProcessorImpl implements UserProcessor {

    private final UserRepository userRepository;

    public UserProcessorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(String userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public void createUser(String userId, String password, String name, String email) {
        userRepository.addUser(new User(userId, password, name, email));
    }

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }
}
