package http.request;

import java.util.Optional;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Headers headers;
    private final String body;

    public HttpRequest(RequestLine requestLine, Headers headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public boolean isStaticFile() {
        return requestLine.isStaticFile();
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody() {
        return body;
    }

    public Optional<String> getCookie(String key) {
        return headers.getCookie(key);
    }

    public String getFileExtension() {
        return requestLine.getFileExtension();
    }
}
