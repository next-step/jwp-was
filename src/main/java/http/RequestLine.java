package http;

public class RequestLine {

    private final String method;
    private final String path;
    private final String protocol;
    private final String version;

    public RequestLine(String value, String value1, String s, String s1) {
        this.method = value;
        this.path = value1;
        this.protocol = s;
        this.version = s1;
    }

    public String getMethod() {
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

    public String getQueryString() {
        return null;
    }
}
