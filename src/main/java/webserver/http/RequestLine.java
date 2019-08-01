package webserver.http;

public class RequestLine {

    private static final String DELIMITER = " ";

    private String httpMethod;
    private String path;
    private String httpVersion;

    private RequestLine(String httpMethod, String path, String httpVersion) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String line) {
        String[] values = line.split(DELIMITER);
        return new RequestLine(values[0], values[1], values[2]);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
