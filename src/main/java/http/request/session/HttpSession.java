package http.request.session;

import java.util.Optional;

public interface HttpSession {
    String getSessionId();
    void setAttribute(String name, Object value);
    Optional<Object> getAttribute(String name);
    void removeAttribute(String name);
    void invalidate();
}
