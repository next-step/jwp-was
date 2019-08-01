package webserver.http;

import com.google.common.base.Strings;

public class RequestLine {

    private static final String token = " ";

    private final HttpMethod method;
    private final String requestUri;

    public static RequestLine parse(String requestLine) {
        if (Strings.isNullOrEmpty(requestLine)) {
            throw new IllegalArgumentException("Request-Line은 필수입니다.");
        }

        final String[] requestLines = requestLine.split(token);

        return new RequestLine(requestLines[0], requestLines[1]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    private RequestLine(String method, String requestUri) {
        this.method = HttpMethod.valueOf(method);
        this.requestUri = requestUri;
    }
}
