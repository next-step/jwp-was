package http;

public class RequestLine {
    private String method;
    private String path;
    private String protocol;
    private String version;

    public RequestLine(String requestLine) {

    }

    public static RequestLine parse(String s) {
        return  null;
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
}
