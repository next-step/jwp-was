package webserver;

/**
 * Created by youngjae.havi on 2019-08-01
 */
public class RequestLine {
    private String method;
    private String path;
    private String httpVersion;

    public RequestLine(String method, String path, String httpVersion) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String requestLine) {
        String[] lines = requestLine.split(" ");
        return new RequestLine(lines[0], lines[1], lines[2]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
