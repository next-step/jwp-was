package webserver.request;

import webserver.request.exception.IllegalRequestLineException;

public class RequestLine {
    private final HttpMethod method;
    private final Uri uri;
    private final Protocol protocol;

    public RequestLine(HttpMethod method, Uri uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return uri.getPath();
    }

    public String getQuery(String key) {
        return uri.getQuery(key);
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public static RequestLine from(String requestLine) {
        if (requestLine == null) {
            throw new IllegalRequestLineException("NULL");
        }
        String[] requestInfo = requestLine.trim().split("\\s+");
        int expectedLength = 3;
        if (requestInfo.length != expectedLength) {
            throw new IllegalRequestLineException(requestLine);
        }
        return new RequestLine(
                HttpMethod.from(requestInfo[0]),
                Uri.from(requestInfo[1]),
                Protocol.from(requestInfo[2])
        );
    }
}
