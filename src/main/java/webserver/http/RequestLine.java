package webserver.http;

public class RequestLine {
    private final String version;
    private final String method;
    private final Parts parts;

    public RequestLine(String method, Parts parts, String version) {
        this.method = method;
        this.parts = parts;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return parts.getPath();
    }

    public String getParameters(String key) {
        return parts.getQuery(key);
    }

    public static RequestLine parse(String requestLine) {
        String[] splitedRequestLine = requestLine.split(" ");
        String method = splitedRequestLine[0];
        String path = splitedRequestLine[1];
        String version = splitedRequestLine[2];
        return new RequestLine(method, Parts.parse(path), version);
    }

}
