package model;

public class RequestLine {
    private String method;
    private String path;
    private String protocol;
    private String version;

    private RequestLine(String method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine parsing(String firstLine) {
        String[] requestDataArray = firstLine.split(" ");
        String method = requestDataArray[0];
        String path = requestDataArray[1];
        String[] protocolAndVersion = requestDataArray[2].split("/");
        return new RequestLine(method, path, protocolAndVersion[0], protocolAndVersion[1]);
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
