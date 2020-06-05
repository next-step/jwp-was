package http;

public class HttpRequestParser {
    private HttpRequestParser() {}

    public static HttpRequest parse(final String rawRequestStr) {
        RawRequest rawRequest = new RawRequest(rawRequestStr);

        return HttpRequest.init(
                RequestLineParser.parse(rawRequest.getRequestLine()),
                new RequestHeader(rawRequest.getHeaders()),
                new RequestBody(rawRequest.getBody())
        );
    }
}
