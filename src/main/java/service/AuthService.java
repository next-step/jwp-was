package service;

import model.HttpHeaders;
import model.Session;
import model.SessionStorage;

public class AuthService {

    public static ThreadLocal<Boolean> userLogined = new ThreadLocal<>();
    private static final AuthService authService = new AuthService();

    private AuthService() {

    }

    public static AuthService getInstance() {
        return authService;
    }

    public void setUserCredential(HttpHeaders requestHeaders) {
        if (this.userLogined(requestHeaders)) {
            userLogined.set(true);
            return;
        }

        userLogined.set(false);
    }

    public void removeUserCredential() {
        userLogined.remove();
    }

    private boolean userLogined(HttpHeaders requestHeaders) {
        String sessionId = requestHeaders.getSessionId();
        if (sessionId == null) {
            return false;
        }

        Session userSession = SessionStorage.getInstance().getSession(sessionId);
        if (userSession == null) {
            return false;
        }

        return userSession.isUserLogined();
    }

}
