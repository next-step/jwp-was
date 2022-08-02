package http.request.session;

import java.util.Map;
import java.util.Optional;

public class DefaultHttpSession implements HttpSession {

    private final Map<String, Object> values;
    private final String sessionId;

    public DefaultHttpSession(Map<String, Object> values, String sessionId) {
        this.values = values;
        this.sessionId = sessionId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setAttribute(String name, Object value) {
        values.put(name, value);
    }

    @Override
    public Optional<Object> getAttribute(String name) {
        return Optional.ofNullable(values.get(name));
    }

    @Override
    public void removeAttribute(String name) {
        values.remove(name);
    }

    @Override
    public void invalidate() {
        values.clear();
    }
}
