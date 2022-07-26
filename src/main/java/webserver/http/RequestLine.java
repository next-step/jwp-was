package webserver.http;

public class RequestLine {
    private final HttpMethod method;
    private final Path path;
    private final Protocol protocol;

    public RequestLine(String requestLine) {
        String[] values = split(requestLine);
        if (values.length != 3) {
            throw new IllegalArgumentException("잘못된 HTTP 요청");
        }
        this.method = HttpMethod.valueOf(values[0]);
        this.path = createPath(values[1]);
        this.protocol = new Protocol(values[2]);
    }

    private Path createPath(String values) {
        if (method.isPost()) {
            return Path.createWithPost(values);
        }
        return Path.createWithGetMethod(values);
    }

    private String[] split(String requestLine) {
        return requestLine.split(" ");
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }
}
