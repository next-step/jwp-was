package domain;

public class RequestLineParser {
    private static final String REQUEST_DELIMITER = " ";

    public HttpRequest parse(String requestLine) {
        final String[] requestSpecs = requestLine.split(REQUEST_DELIMITER);
        return new HttpRequest(
                HttpMethod.valueOf(requestSpecs[0]),
                new HttpPath(requestSpecs[1]),
                new HttpProtocol(requestSpecs[2])
        );
    }
}
