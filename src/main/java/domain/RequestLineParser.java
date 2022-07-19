package domain;

public class RequestLineParser {
    public HttpRequest parse(String requestLine) {
        final String[] requestSpecs = requestLine.split(" ");
        final String[] protocolSpecs = requestSpecs[2].split("/");
        return new HttpRequest(requestSpecs[0], requestSpecs[1], protocolSpecs[0], protocolSpecs[1]);
    }
}
