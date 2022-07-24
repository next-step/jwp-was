package webserver.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import model.User;

public class Users {

    private final Map<String, User> map = new ConcurrentHashMap<>();

    public void save(String userId, User user) {
        map.put(userId, user);
    }

    public Optional<User> findBy(String userId) {
        var user = map.get(userId);

        return Optional.ofNullable(user);
    }
}
