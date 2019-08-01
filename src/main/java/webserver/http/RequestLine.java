package webserver.http;

public class RequestLine {

    private String method;
    private String path;
    private String protocol;

    private RequestLine(String method, String path, String protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine parse(String requestLine) {
        String[] requestLineSplit = requestLine.split(" ");

        return new RequestLine(requestLineSplit[0], requestLineSplit[1], requestLineSplit[2]);
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
}
