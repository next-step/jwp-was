package webserver.http;

public class RequestLine {

    private static final String SEPARATOR = " ";
    private static final int INDEX_OF_REQUEST_METHOD = 0;
    private static final int INDEX_OF_REQUEST_URL = 1;

    private final RequestMethod method;
    private final RequestURL url;

    private RequestLine(final RequestMethod method,
                        final RequestURL url) {
        this.method = method;
        this.url = url;
    }

    public static RequestLine parse(final String rawRequestLine) {
        final String[] splitRequestLine = rawRequestLine.split(SEPARATOR);
        final RequestMethod requestMethod = RequestMethod.of(splitRequestLine[INDEX_OF_REQUEST_METHOD]);
        final RequestURL requestURL = RequestURL.of(splitRequestLine[INDEX_OF_REQUEST_URL]);

        return new RequestLine(requestMethod, requestURL);
    }

    public String getMethod() {
        return method.name();
    }

    public String getPath() {
        return url.getPath();
    }
}
