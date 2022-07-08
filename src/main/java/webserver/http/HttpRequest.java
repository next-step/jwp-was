package webserver.http;

import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;

    private final Map<String, String> headers;

    private final QueryString queryString;

    public HttpRequest(final RequestLine requestLine, final Map<String, String> headers, final String requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.queryString = QueryString.parse(requestBody);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        if (headers == null) {
            return "";
        }

        return headers.getOrDefault(name, "")
                .split(",")[0];
    }

    public String getParameter(String name) {
        return getQueryString().get(name);
    }

    private QueryString getQueryString() {
        QueryString parse = requestLine.toQueryString();

        if (getMethod() == HttpMethod.POST) {
            parse = queryString;
        }

        return parse;
    }
}
