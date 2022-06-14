package webserver.request;

import webserver.common.Protocol;
import webserver.request.exception.IllegalRequestLineException;

public class RequestLine {
    private final RequestMethod method;
    private final Uri uri;
    private final Protocol protocol;

    public RequestLine(RequestMethod method, Uri uri) {
        this.method = method;
        this.uri = uri;
        this.protocol = Protocol.HTTP_1_1;
    }

    private RequestLine(RequestMethod method, Uri uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public RequestMethod getMethod() {
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

    public String hash() {
        return String.format("%s %s", getMethod(), getPath());
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
                RequestMethod.from(requestInfo[0]),
                Uri.from(requestInfo[1]),
                Protocol.from(requestInfo[2])
        );
    }
}
