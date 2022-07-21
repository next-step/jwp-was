package webserver.http.request;

public class RequestLine {
    private final Method method;
    private final URI uri;
    private final Protocol protocol;

    public RequestLine(Method method, URI uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public Method getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public Protocol getProtocol() {
        return protocol;
    }
}
