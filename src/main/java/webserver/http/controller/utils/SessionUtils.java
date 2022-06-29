package webserver.http.controller.utils;

import model.User;
import webserver.http.session.HttpSession;

public class SessionUtils {
    private static final String USER_ATTRIBUTE_NAME = "user";

    public static void setUser(final HttpSession session, final User user) {
        session.setAttribute(USER_ATTRIBUTE_NAME, user);
    }

    public static User getUserOrNull(final HttpSession session) {
        return (User) session.getAttributeOrNull(USER_ATTRIBUTE_NAME);
    }
}
