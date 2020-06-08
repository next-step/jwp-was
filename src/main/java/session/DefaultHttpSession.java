package session;

import java.util.HashMap;
import java.util.Map;

public class DefaultHttpSession implements HttpSession {
    private String sessionId;
    private final Map<String, Object> attributes = new HashMap<>();

    private DefaultHttpSession() {}

    public static DefaultHttpSession of(final String sessionId) {
        DefaultHttpSession httpSession = new DefaultHttpSession();
        httpSession.sessionId = sessionId;

        return httpSession;
    }

    @Override
    public String getId() {
        return sessionId;
    }

    @Override
    public void setAttribute(final String name, final Object value) {
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(final String name) {
        return attributes.get(name);
    }

    @Override
    public void removeAttribute(final String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        attributes.clear();
    }
}
