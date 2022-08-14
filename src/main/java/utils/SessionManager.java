package utils;

import model.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static Map<String, HttpSession> sessions = null;

    private SessionManager() {
    }

    public static Map<String, HttpSession> getSessions() {
        if (sessions == null) {
            sessions = new HashMap<>();
        }
        return sessions;
    }
}
