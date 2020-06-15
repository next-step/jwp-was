package http.request;

import session.HttpSessionManager;
import session.Session;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private RequestLine requestLine;

    private HttpRequestHeader requestHeaders;

    private Map<String, String> parameters;

    private Session session;

    public HttpRequest(final RequestLine requestLine, final HttpRequestHeader requestHeader, final Map<String, String> parameters) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeader;
        this.parameters = parameters;

        if (this.parameters == null) this.parameters = new HashMap<>();

        this.session = HttpSessionManager.getSession(requestHeader.getHeader("SESSIONID"));
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getProtocol() {
        return requestLine.getProtocol();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public String getHeader(String key) {
        return requestHeaders.getHeader(key);
    }

    public String getParameter(String key) {
        return requestLine.getParameter(key) != null ? requestLine.getParameter(key) : parameters.get(key);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Object getSessionAttribute(String key) {
        if (session == null) {
            throw new NullPointerException("Session invalid");
        }

        return session.getAttribute(key);
    }

    public void setSessionAttribute(String key, String value) {
        if (session == null) {
            throw new NullPointerException("Session invalid");
        }

        session.setAttribute(key, value);
    }
}
