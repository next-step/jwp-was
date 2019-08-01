package webserver.http;

public class RequestLine {
    private static final String SP_CHARACTERS = "\\s";

    private final Method method;
    private final String requestUri;
    private final HttpVersion httpVersion;

    private RequestLine(final Method method, final String requestUri, final HttpVersion httpVersion) {
        this.method = method;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(final String line) {
        final String[] elements = line.split(SP_CHARACTERS);
        return new RequestLine(
                Method.valueOf(elements[0]),
                elements[1],
                HttpVersion.parse(elements[2])
        );
    }

    public String getMethod() {
        return method.toString();
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getHttpVersion() {
        return httpVersion.toString();
    }
}
