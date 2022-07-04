package service;

import db.DataBase;
import model.User;
import webserver.session.HttpSession;
import webserver.session.HttpSessionManager;

import java.util.Objects;

public class UserLoginService {

    private static final String SESSION_USER_KEY = "user";

    public User login(String sessionId) throws FailedLoginException {
        HttpSession session = HttpSessionManager.getSession(sessionId);
        if (Objects.isNull(session)) {
            throw new FailedLoginException(sessionId);
        }

        Object user = session.getAttribute(SESSION_USER_KEY);
        if (Objects.isNull(user) || !(user instanceof User)) {
            throw new FailedLoginException(sessionId);
        }

        return (User) user;
    }

    public String login(String userId, String password) throws FailedLoginException {
        User user = DataBase.findUserById(userId);
        if (Objects.isNull(user)) {
            throw new FailedLoginException(userId);
        }

        if (!user.matchPassword(password)) {
            throw new FailedLoginException(password);
        }

        HttpSession session = HttpSessionManager.createSession();
        session.setAttribute("user", user);

        return session.getId();
    }
}
