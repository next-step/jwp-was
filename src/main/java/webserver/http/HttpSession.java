package webserver.http;

public class HttpSession {
    private SessionData sessionData;
    private String id;

    public HttpSession(String id) {
        this.id = id;
        this.sessionData = new SessionData();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        sessionData.put(name, value);
    }

    public Object getAttribute(String name) {
        return sessionData.getObject(name);
    }

    public void removeAttribute(String name) {
        sessionData.removeSessionData(name);
    }
}
