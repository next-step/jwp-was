package http;

public class HttpRequestParser {
    private HttpRequestParser() {}

    public static HttpRequest parse(final String rawRequestStr) {
        RawRequest rawRequest = new RawRequest(rawRequestStr);

        String requestLine = rawRequest.getRequestLine();

        return HttpRequest.init(RequestLineParser.parse(requestLine));
    }
}
