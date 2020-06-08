package http.request;

import webserver.session.HttpSession;
import webserver.session.Sessions;

public class Request {
    private static final String JSESSIONID = "JSESSIONID";

    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestBody requestBody;

    public Request(RequestLine requestLine, Headers headers, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getHeader(String key) {
        return this.headers.getHeader(key);
    }

    public Headers getHeaders() {
        return headers;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public HttpSession getSession() {
        String sessionId = this.headers.getHeader(JSESSIONID);
        return Sessions.findById(sessionId);
    }
}
