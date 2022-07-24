package webserver.http.response;

public class HttpResponseMessage {
    public static final String RESPONSE_END_OF_LINE_MARKER = "\r\n";

    private HttpResponseStatusLine httpResponseStatusLine;
    private HttpResponseBody httpResponseBody;
    private HttpResponseHeaders httpResponseHeaders;
}
