package http;

public interface Session {
    String getId();
    void setAttribute(String name, Object value);
    Object getAttribute(String name);
    void removeAttribute(String name);
    void invalidate();
}
