package webserver.http.request.start_line;

import org.springframework.http.HttpMethod;

public class HttpRequestLine {

    private static final String DELIMITER = "/";

    private final HttpMethod httpMethod;
    private final RequestPath path;
    private final Protocol protocol;
    private final HttpVersion version;

    public HttpRequestLine(final String requestLine) {
        final String[] split = requestLine.split(" ");

        this.httpMethod = HttpMethod.resolve(split[0]);
        this.path = new RequestPath(split[1]);
        this.protocol = new Protocol(split[2].split(DELIMITER)[0]);
        this.version = new HttpVersion(split[2].split(DELIMITER)[1]);
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
