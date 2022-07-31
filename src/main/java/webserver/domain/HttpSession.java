package webserver.domain;

public interface HttpSession {
    String SESSION_COOKIE_NAME = "sessionId";

    String getId();

    void setAttribute(String name, Object value);

    Object getAttribute(String name);

    void removeAttribute(String name);

    void invalidate();

    Cookie getCookie();
}
