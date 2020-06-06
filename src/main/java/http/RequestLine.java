package http;

import static http.HttpMethod.*;

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

    public QueryString getQueryString() {
        return this.path.getQueryString();
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public boolean isExistQuery() {
        return path.isExistQuery();
    }

    public void addQueryString(String body) {
        this.path.addQueryString(body);
    }

    public boolean isPost() {
        return this.method == POST;
    }
}
