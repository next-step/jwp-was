package webserver.http;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class Request {
    private static final int REQUEST_LINE_IDX = 0;

    private static final int REQUEST_HEADER_IDX = 1;

    private final RequestLine requestLine;

    private final Headers headers;

    Request(RequestLine requestLine, Headers headers) {
        this.requestLine = requireNonNull(requestLine, "");
        this.headers = requireNonNull(headers, "");
    }

    public static Request parseOf(List<String> requestLines) {
        return new Request(
                RequestLine.parseOf(requestLines.get(REQUEST_LINE_IDX)),
                Headers.parseOf(requestLines.subList(REQUEST_HEADER_IDX, requestLines.size()))
        );
    }

    public String getPath() {
        return requestLine.getPath().getPath();
    }

    public Headers getHeaders() {
        return headers;
    }

    RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestParameters getParameters() {
        return requestLine.getPath().parseQueryString();
    }

    @Override
    public String toString() {
        return requestLine.toString() + " " + headers.toString();
    }
}
