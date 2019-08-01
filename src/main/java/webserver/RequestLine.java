package webserver;

import org.springframework.util.Assert;

public class RequestLine {

    private String method;
    private String path;
    private String spec;

    public RequestLine(String method, String path, String spec) {
        this.method = method;
        this.path = path;
        this.spec = spec;
    }

    public static RequestLine parse(String requestLine) {
        Assert.notNull(requestLine, "requestLine must be not null");
        String[] requests = requestLine.split(" ");

        if (requests.length != 3) {
            throw new IllegalArgumentException("invalid arguments [" + requestLine + "]");
        }

        return new RequestLine(requests[0], requests[1], requests[2]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getSpec() {
        return spec;
    }
}
