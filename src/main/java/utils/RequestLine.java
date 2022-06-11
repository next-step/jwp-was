package utils;

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

    public static RequestLine parse(String readLine) {
        String[] tokens = readLine.split(" ");

        String method = tokens[0];
        String path = tokens[1];
        String protocol = tokens[2].split("/")[0];
        String version = tokens[2].split("/")[1];

        return new RequestLine(method, path, protocol, version);
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
