package http;

public class RequestLine {

    private HttpMethodType method;
    private String path;
    private Protocol protocol;

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = HttpMethodType.valueOf(method);
        this.path = path;
        this.protocol = protocol;
    }

    public HttpMethodType getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

}
