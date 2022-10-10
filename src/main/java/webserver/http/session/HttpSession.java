package webserver.http.session;

import java.util.Objects;

public class HttpSession {

    private final SessionId id;
    private final SessionAttributes attributes;

    public HttpSession() {
        this.id = SessionId.generate();
        this.attributes = new SessionAttributes();
    }

    public SessionId getId() {
        return id;
    }

    public Object getAttributes(String name) {
        return attributes.getAttribute(name);
    }

    public int getAttributesSize() {
        return attributes.getAttributesSize();
    }

    public void setAttributes(String name, Object value) {
        attributes.setAttribute(name, value);
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

    @Override
    public String toString() {
        return "HttpSession{" +
                "id=" + id +
                ", attributes=" + attributes +
                '}';
    }
}
