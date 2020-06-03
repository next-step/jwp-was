package http;

public class RequestLine {

    private HttpMethodType method;
    private String path;
    private String protocol;
    private String version;

    public RequestLine(String method, String path, String protocol, String version) {
        this.method = HttpMethodType.valueOf(method);
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public HttpMethodType getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
