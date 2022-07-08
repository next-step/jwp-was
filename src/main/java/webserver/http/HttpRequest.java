package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;

    private final Map<String, String> headers;

    private final Map<String, String> params;

    public HttpRequest(final RequestLine requestLine, final Map<String, String> headers, final String requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.params = getParams(requestLine, requestBody);
    }

    private Map<String, String> getParams(RequestLine requestLine, String requestBody) {
        Map<String, String> params = new HashMap<>();

        QueryString searchParams = requestLine.toQueryString();
        params.putAll(searchParams.toParams());

        QueryString bodyParams = QueryString.parse(requestBody);
        params.putAll(bodyParams.toParams());

        return params;
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
        return params.get(name);
    }
}
