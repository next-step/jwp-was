package utils;

import http.request.HttpRequest;

public class LoginUtil {

    public static final String LOGGED_IN = "logined";
    private static final String TRUE = "true";

    private LoginUtil() {}

    public static boolean isLoggedIn(final HttpRequest request) {
        String loggedInValue = request.getCookie(LOGGED_IN);

        return TRUE.equals(loggedInValue);
    }
}
