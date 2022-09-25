package webserver.http.domain.session;

public class HttpSession {
    private final SessionId sessionId;
    private final SessionAttributes sessionAttributes;

    public HttpSession() {
        this.sessionId = new SessionId();
        this.sessionAttributes = new SessionAttributes();
    }

    public HttpSession(String sessionId) {
        this.sessionId = new SessionId(sessionId);
        this.sessionAttributes = new SessionAttributes();
    }

    public SessionId sessionId() {
        return sessionId;
    }

    public String getId() {
        return sessionId.id();
    }

    public Object getAttribute(String name) {
        return sessionAttributes.attribute(name);
    }

    public void setAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
        SessionStorage.add(this);
    }

    public void removeAttribute(String name) {
        sessionAttributes.remove(name);
        SessionStorage.remove(sessionId);
    }

    public void invalidate() {
        sessionAttributes.clear();
        SessionStorage.invalidate();
    }
}
