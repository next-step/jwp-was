package webserver.http;

public class RequestLine {
    private String method;
    private String path;

    private RequestLine(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestLine parse(String path) {
        String[] pathInfo = path.split(" ");
        return new RequestLine(pathInfo[0], pathInfo[1]);
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }
}
