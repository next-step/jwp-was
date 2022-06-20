package webserver.application;

import webserver.domain.user.User;
import webserver.domain.user.UserRepository;

import java.util.Collection;
import java.util.Objects;

public class UserProcessorImpl implements UserProcessor {

    private final UserRepository userRepository;

    public UserProcessorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValidUser(String userId, String password) {
        User user = userRepository.findUserById(userId);
        return Objects.nonNull(user) && user.getPassword().equals(password);
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
