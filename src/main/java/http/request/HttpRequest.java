package http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private RequestLine requestLine;

    private HttpRequestHeader requestHeaders;

    private Map<String, String> parameters;

    public HttpRequest(final RequestLine requestLine, final HttpRequestHeader requestHeader, final Map<String, String> parameters) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeader;
        this.parameters = parameters;

        if (this.parameters == null) this.parameters = new HashMap<>();
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
        if (requestLine.isGet()) {
            return requestLine.getParameter(key);
        }
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        if (requestLine.isGet()) {
            return requestLine.getParameters();
        }
        return parameters;
    }
}
