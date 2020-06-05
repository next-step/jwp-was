package utils;

import http.HttpRequest;

public class LoginUtil {
    private LoginUtil() {}

    public static boolean isLogedIn(final HttpRequest request) {
        String logined = request.getCookie("logined");

        return "true".equals(logined);
    }
}
