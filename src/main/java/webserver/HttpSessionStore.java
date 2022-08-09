package webserver;

import webserver.http.HttpSession;

public interface HttpSessionStore {

    HttpSession createHttpSession();

    HttpSession getSession(String sessionId);
}
