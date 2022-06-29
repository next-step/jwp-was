package webserver.session;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class HttpSessionImpl implements HttpSession {

    private final UUID uuid = UUID.randomUUID();
    private final Map<String, Object> session = new HashMap<>();

    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return session.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        session.remove(name);
    }

    @Override
    public void invalidate() {
        session.clear();
    }

    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {

    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getValue(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void putValue(String name, Object value) {

    }

    @Override
    public void removeValue(String name) {

    }

    @Override
    public boolean isNew() {
        return false;
    }
}
