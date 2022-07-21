package webserver;

import org.springframework.http.HttpMethod;

public class HttpRequestLine {

    private final HttpMethod httpMethod;
    private final RequestPath path;
    private final Protocol protocol;
    private final HttpVersion version;

    public HttpRequestLine(final String requestLine) {
        this.httpMethod = HttpMethod.resolve(requestLine.split(" ")[0]);
        this.path = new RequestPath(requestLine);
        this.protocol = new Protocol(requestLine);
        this.version = new HttpVersion(requestLine);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public RequestPath getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public HttpVersion getVersion() {
        return version;
    }
}
