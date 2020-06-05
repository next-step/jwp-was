package http;

public class RequestLine {

    private HttpMethodType method;
    private Path path;
    private Protocol protocol;

    public RequestLine(String method, Path path, Protocol protocol) {

        this.method = HttpMethodType.valueOf(method);
        this.path = path;
        this.protocol = protocol;
    }

    public HttpMethodType getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public boolean isExistQuery() {
        return path.isExistQuery();
    }

}
