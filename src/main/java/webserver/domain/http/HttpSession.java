package webserver.domain.http;

public interface HttpSession {

    String SESSION_ID = "JSESSIONID";

    String getId();

    void setAttribute(String name, Object value);

    Object getAttribute(String name);

    void removeAttribute(String name);

    void invalidate();
}
