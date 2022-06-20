package webserver.domain.user;

import java.util.Collection;

public interface UserRepository {

    User findUserById(String userId);

    void addUser(User user);

    Collection<User> findAll();
}
