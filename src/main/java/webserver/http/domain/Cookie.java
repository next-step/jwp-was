package webserver.http.domain;

import webserver.http.domain.session.SessionId;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Cookie {

    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String EMPTY_VALUE = "";

    public static final String LOGINED = "logined";

    private static final Cookie cookie = new Cookie();
    private final Map<String, String> cookies = new HashMap<>();

    private Cookie() {

    }

    public static Cookie getInstance() {
        return cookie;
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public void setLoginCookie(boolean isLogined, String path) {
        this.addCookie("Set-Cookie", "logined=" + isLogined + "; Path=" + path);
    }

    public SessionId sessionId(String key) {
        if (!cookies.containsKey(key)) {
            throw new NoSuchElementException();
        }
        return new SessionId(cookies.get(key));
    }
}
