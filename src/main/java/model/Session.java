package model;

import java.util.HashMap;
import java.util.Map;

public class Session {

    public static final String LOGIN_HEADER_KEY = "logined";
    private final Map<String, Object> storage;

    public Session() {
        this.storage = new HashMap<>();
    }

    public Object getId() {
        return String.valueOf(this.storage.get(SessionStorage.UUID));
    }

    public void setAttribute(String key, Object value) {
        this.storage.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.storage.get(key);
    }

    public void removeAttribute(String key) {
        this.storage.remove(key);
    }

    public void invalidate() {
        this.storage.clear();
    }

    public boolean isUserLogined() {
        Object logined = this.storage.get(LOGIN_HEADER_KEY);
        if (logined == null) {
            return false;
        }

        return Boolean.parseBoolean(String.valueOf(logined));
    }

    public void setUserLogined() {
        this.storage.put(LOGIN_HEADER_KEY, true);
    }

}
