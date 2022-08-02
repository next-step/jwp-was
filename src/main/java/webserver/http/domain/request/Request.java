package webserver.http.domain.request;

import webserver.http.domain.Headers;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                '}';
    }
}
