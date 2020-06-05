package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class DataBase {
    private static final Logger logger = LoggerFactory.getLogger(DataBase.class);

    private static final Map<String, User> USERS = Maps.newHashMap();

    public static void addUser(final User user) {
        logger.debug("New user add in memory db " + user);

        USERS.put(user.getUserId(), user);
    }

    public static User findUserById(final String userId) {
        return USERS.get(userId);
    }

    public static Collection<User> findAll() {
        return USERS.values();
    }
}
