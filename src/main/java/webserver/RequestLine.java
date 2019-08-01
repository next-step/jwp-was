package webserver;

public class RequestLine {

    private static final String DELEMETER = " ";
    private String method;
    private String requestUri;
    private String httpVersion;

    public String getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public RequestLine(String method, String requestUri, String httpVersion) {
        this.method = method;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String requestLineString) {
        String[] piece = requestLineString.split(DELEMETER);
        return new RequestLine(piece[0], piece[1], piece[2]);
    }
}
