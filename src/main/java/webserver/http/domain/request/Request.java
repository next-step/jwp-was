package webserver.http.domain.request;

import webserver.http.domain.cookie.Cookie;
import webserver.http.domain.Headers;
import webserver.http.domain.session.Session;
import webserver.http.domain.session.SessionContextHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Request {
    private final RequestLine requestLine;
    private final Headers headers;

    private final Map<String, Object> attributes = new HashMap<>();
    public Request(RequestLine requestLine, Headers headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public boolean hasContents() {
        return headers.containsContentLength();
    }

    public int getContentLength() {
        return headers.getContentLength();
    }

    public void addParameters(Parameters parameters) {
        requestLine.addParameters(parameters);
    }

    public String getHeader(String name) {
        return headers.getValue(name);
    }

    public int getHeaderAsInt(String name) {
        return headers.getValueAsInt(name);
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public boolean hasMethod(Method method) {
        return requestLine.hasMethod(method);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public boolean hasPath(String path) {
        return getPath().equals(path);
    }

    public String getParameter(String key) {
        return requestLine.getParameter(key);
    }

    public int getParameterAsInt(String key) {
        try {
            return Integer.parseInt(getParameter(key));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자방식이 아닌 리터럴 값은 인자로 들어갈 수 없습니다.", e);
        }
    }

    public boolean hasContentType(String contentType) {
        return headers.hasContentType(contentType);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Map<String, Object> getAttributes() {
        return Map.copyOf(attributes);
    }

    public boolean existsCookie(String name, String value) {
        return headers.existsCookie(name, value);
    }

    public Session getSession() {
        return SessionContextHolder.getCurrentSession();
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                '}';
    }

    public Optional<Cookie> getCookie(String name) {
        return headers.getCookie(name);
    }
}
