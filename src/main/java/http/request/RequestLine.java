package http.request;

import static http.request.HttpMethod.valueOf;

public class RequestLine {

    private HttpMethod method;
    private Path path;
    private Protocol protocol;

    public RequestLine(String method, Path path, Protocol protocol) {

        this.method = valueOf(method);
        this.path = path;
        this.protocol = protocol;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return this.path.getPath();
    }

    public Protocol getProtocol() {
        return protocol;
    }

}
