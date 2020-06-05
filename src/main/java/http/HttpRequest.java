package http;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpHeaderInfo headerInfo;
    private final String requestBody;

    public HttpRequest(RequestLine requestLine, HttpHeaderInfo headerInfo, String requestBody) {
        this.requestLine = requestLine;
        this.headerInfo = headerInfo;
        this.requestBody = requestBody;
    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }

    public String getRequestBody() {
        return this.requestBody;
    }
}
