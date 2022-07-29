package service;

import utils.HttpParser;

import java.util.Map;

public class AuthService {

    public static final String LOGIN_HEADER_KEY = "logined";

    public static ThreadLocal<Boolean> userLogined = new ThreadLocal<>();
    private static final AuthService authService = new AuthService();
    private static final String COOKIE_HEADER_KEY = "Cookie";

    private AuthService() {

    }

    public static AuthService getInstance() {
        return authService;
    }

    public void setUserCredential(Map<String, String> requestHeaders) {
        if (this.parseLoginedFromCookie(requestHeaders)) {
            userLogined.set(true);
            return;
        }

        userLogined.set(false);
    }

    public void removeUserCredential() {
        userLogined.remove();
    }

    private boolean parseLoginedFromCookie(Map<String, String> requestHeaders) {
        String cookieValue = requestHeaders.get(COOKIE_HEADER_KEY);
        return (cookieValue != null && Boolean.parseBoolean(cookieValue.split(HttpParser.QUERY_PARAMETER_KEY_VALUE_SEPARATOR)[1]));
    }

}
