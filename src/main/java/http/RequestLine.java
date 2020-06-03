package http;

public class RequestLine {
    private String method;
    private String path;
    private String protocol;
    private String version;

    public RequestLine(String requestLine) {
        String[] str = requestLine.split(" ");
        this.method = str[0];
        this.path = str[1];
        String[] protocolAndVersion = str[2].split("/");
        this.protocol = protocolAndVersion[0];
        this.version = protocolAndVersion[1];
    }

    public static RequestLine parse(String s) {
        return new RequestLine(s);
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
