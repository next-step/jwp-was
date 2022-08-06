package webserver.http.model;

import java.util.Arrays;
import java.util.Map;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;

    public HttpRequest(String httpRequestText) {
        String[] texts = httpRequestText.split("\n");

        requestLine = new RequestLine(texts[0]);
        requestHeaders = new RequestHeaders(Arrays.stream(texts).filter(text -> !text.equals(texts[0])).toArray(String[]::new));
    }

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
    }

    public String responsePath() {
        return requestLine.fullPath();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders.getRequestHeadersMap();
    }
}