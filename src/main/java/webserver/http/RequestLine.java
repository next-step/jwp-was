package webserver.http;

public class RequestLine {

    private final String REQUEST_SEPARATOR = " ";
    private final String URI_SEPARATOR = "/";

    private final HttpRequest httpRequest;

    public RequestLine(String request) {

        final String[] requestInfo = request.split(REQUEST_SEPARATOR);
        final String[] uriInfo = requestInfo[2].split(URI_SEPARATOR);

        this.httpRequest = new HttpRequest(requestInfo[0], requestInfo[1], uriInfo[0], uriInfo[1]);
    }

    public HttpRequest toRequest() {
        return this.httpRequest;
    }
}
