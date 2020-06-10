package http.request;

import static http.request.HttpMethod.GET;
import static http.request.HttpMethod.POST;
import static http.request.HttpMethod.valueOf;

public class RequestLine {

    private final static String REQUEST_LINE_DELIMITER = " ";
    private HttpMethod method;
    private Path path;
    private Protocol protocol;

    public RequestLine(String value) {
        String[] values = value.split(REQUEST_LINE_DELIMITER);
        this.method = valueOf(values[0]);
        this.path = new Path(values[1]);
        this.protocol = new Protocol(values[2]);
    }

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

    public String getParameter(String key) {
        return path.getParameter(key);
    }

    public QueryString getParameters() {
        return path.getQueryString();
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public boolean isPost() {
        return method == POST;
    }

    public boolean isGet() {
        return method == GET;
    }

}
