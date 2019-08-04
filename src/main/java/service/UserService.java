package service;

import com.google.common.collect.Lists;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private Map<String, User> users = new HashMap<String, User>(){{
        put("root", new User("root", "1234", "관리자", "admin@test.com"));
    }};

    private UserService() {}

    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    public User add(final User user) {
        if (users.containsKey(user.getUserId())) {
            throw new IllegalArgumentException("user can not be duplicated");
        }

        logger.debug("add user into user registry, user: {}", user);
        return users.put(user.getUserId(), user);
    }

    public User get(final String userId) {
        return users.get(userId);
    }

    public List<User> getAll() {
        logger.debug("get all user, {}", users.values());
        return Lists.newArrayList(users.values());
    }
}
