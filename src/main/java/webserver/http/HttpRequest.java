package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HttpRequest implements Request {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;

    private final Map<String, String> headers;

    private final QueryString queryString;

    public HttpRequest(final RequestLine requestLine, final Map<String, String> headers, final String requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.queryString = QueryString.parse(requestBody);
    }

    @Override
    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    @Override
    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public String getHeader(String name) {
        return headers.getOrDefault(name, "")
                .split(",")[0];
    }

    @Override
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
