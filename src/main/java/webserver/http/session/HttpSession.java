package webserver.http.session;

import java.util.Objects;
import java.util.Optional;

public class HttpSession implements Session {

    private final SessionId sessionId;
    private final SessionAttributes sessionAttributes;

    public HttpSession() {
        this.sessionId = SessionId.generate();
        this.sessionAttributes = new SessionAttributes();
    }

    @Override
    public String getId() {
        return sessionId.getId();
    }

    @Override
    public Optional<Object> getAttribute(String name) {
        return Optional.ofNullable(sessionAttributes.getAttribute(name));
    }

    public int getAttributesSize() {
        return sessionAttributes.getAttributesSize();
    }

    @Override
    public void setAttribute(String name, Object value) {
        sessionAttributes.setAttribute(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        sessionAttributes.removeAttribute(name);
    }

    @Override
    public void invalidate() {
        sessionAttributes.invalidate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSession that = (HttpSession) o;
        return Objects.equals(sessionId, that.sessionId) && Objects.equals(sessionAttributes, that.sessionAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, sessionAttributes);
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "id=" + sessionId +
                ", attributes=" + sessionAttributes +
                '}';
    }
}
