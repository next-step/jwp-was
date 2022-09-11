package webserver.http.session;


import java.util.Objects;

public class HttpSession {

    public static final String KEY = "JSESSIONID";

    private final SessionId id;
    private final SessionAttributes attributes = new SessionAttributes();

    public HttpSession() {
        this.id = SessionId.newInstance();
    }

    public HttpSession(SessionIdGenerator generator) {
        this.id = SessionId.generateFrom(generator);
    }

    public SessionId getSessionId(){
        return id;
    }

    public String getId(){
        return id.getId();
    }

    public void setAttribute(String name, Object value) {
        attributes.setAttribute(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.getAttribute(name);
    }

    public void removeAttribute(String name) {
        attributes.removeAttribute(name);
    }

    public void invalidate() {
        attributes.invalidate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSession that = (HttpSession) o;
        return Objects.equals(id, that.id) && Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attributes);
    }
}
