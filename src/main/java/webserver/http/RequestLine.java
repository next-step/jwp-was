package webserver.http;

public class RequestLine {

    private static final String REQUEST_SEPARATOR = " ";

    private final HttpRequest httpRequest;

    public RequestLine(final String request) {

        final String[] requestInfo = request.split(REQUEST_SEPARATOR);

        this.httpRequest = new HttpRequest(requestInfo[0], requestInfo[1], requestInfo[2]);
    }

    public HttpRequest toRequest() {
        return this.httpRequest;
    }
}
