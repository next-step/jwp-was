package webserver.http;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class Request {
    private static final int REQUEST_LINE_IDX = 0;

    private static final int REQUEST_HEADER_IDX = 1;

    private final RequestLine requestLine;

    private final Headers headers;

    private final RequestBody requestBody;

    Request(RequestLine requestLine, Headers headers) {
        this(requestLine, headers, null);
    }

    Request(RequestLine requestLine, Headers headers, RequestBody requestBody) {
        this.requestLine = requireNonNull(requestLine, "");
        this.headers = requireNonNull(headers, "");
        this.requestBody = requestBody;
    }

    public static Request parseOf(List<String> requestLines) {
        return new Request(
                RequestLine.parseOf(requestLines.get(REQUEST_LINE_IDX)),
                Headers.parseOf(requestLines.subList(REQUEST_HEADER_IDX, requestLines.size() - 1)),
                new RequestBody(requestLines.get(requestLines.size() - 1))
        );
    }

    RequestLine getRequestLine() {
        return requestLine;
    }

    public String getPath() {
        return requestLine.getPath().getPath();
    }

    public Headers getHeaders() {
        return headers;
    }

    public RequestParameters getParameters() {
        return requestLine.getPath().parseQueryString();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return requestLine.toString() + " " + headers.toString();
    }
}
