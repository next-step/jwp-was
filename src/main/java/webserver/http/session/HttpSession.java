package webserver.http.session;

import java.util.UUID;

public class HttpSession {
    private static final String LOG_IN = "isLogin";

    private final UUID uuid;
    private final SessionAttribute attribute;

    public HttpSession(UUID uuid) {
        this.uuid = uuid;
        this.attribute = new SessionAttribute();
    }

    public String getId() {
        return uuid.toString();
    }

    public void setLogin(boolean isLogin) {
        setAttribute(LOG_IN, isLogin);
    }

    public boolean getLogin() {
        return (Boolean) getAttribute(LOG_IN);
    }

    private void setAttribute(String name, Object value) {
        attribute.setAttribute(name, value);
    }

    public Object getAttribute(String name) {
        return attribute.getAttribute(name);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
        attribute.clear();
    }
}
