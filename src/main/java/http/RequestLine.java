package http;

import org.springframework.http.HttpMethod;

public class RequestLine {

    private final String method;
    private final String path;
    Protocol protocol;

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine of(HttpMethod get, String s, Protocol protocol) {

    }

    public String getMapping() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

}
