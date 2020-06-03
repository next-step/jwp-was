package http;

public class RequestLine {
    private String method;
    private String path;
    private String protocol;
    private String version;

    public RequestLine(String method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public RequestLine of (String requestLine) {
        String[] values = requestLine.split(" ");
        String[] protocolValues = values[2].split("/");

        return new RequestLine(values[0], values[1], protocolValues[0], protocolValues[1]);
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getVersion() {
        return this.version;
    }
}
