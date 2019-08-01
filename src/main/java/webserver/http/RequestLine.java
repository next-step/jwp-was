package webserver.http;

public class RequestLine {
    private static final String SP_CHARACTERS = "\\s";
    private final String method;
    private final String requestUri;
    private final String httpVersion;

    public RequestLine(final String method, final String requestUri, final String httpVersion) {
        this.method = method;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(final String line) {
        final String[] elements = line.split(SP_CHARACTERS);
        return new RequestLine(elements[0], elements[1], elements[2]);
    }

    public String getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
