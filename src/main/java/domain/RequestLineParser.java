package domain;

public class RequestLineParser {
    public HttpRequest parse(String requestLine) {
        final String[] requestSpecs = requestLine.split(" ");
        return new HttpRequest(
                new HttpMethod(requestSpecs[0]),
                new HttpPath(requestSpecs[1]),
                new HttpProtocol(requestSpecs[2])
        );
    }
}
