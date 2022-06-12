package utils;

public class RequestLine {

    private String method;

    private String path;

    private String queryString;

    private String protocol;

    private String version;

    public RequestLine(String method, String path, String queryString, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine parse(String readLine) {
        String[] tokens = readLine.split(" ");

        String method = tokens[0];
        String path = tokens[1].split("\\?")[0] == null ? tokens[1] : tokens[1].split("\\?")[0];
        String queryString = tokens[1].split("\\?").length > 1 ? tokens[1].split("\\?")[1] : null;
        String protocol = tokens[2].split("/")[0];
        String version = tokens[2].split("/")[1];

        return new RequestLine(method, path, queryString, protocol, version);
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
        return queryString;
    }
}
