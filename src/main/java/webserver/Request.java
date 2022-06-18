package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private final RequestLine requestLine;

    private final Map<String, String> headers;

    private final String requestBody;

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Request(final RequestLine requestLine, final Map<String, String> requestHeaders, final String requestBody) {
        this.requestLine = requestLine;
        this.headers = requestHeaders;
        this.requestBody = requestBody;
    }

    public String getContentType() {
        if (headers == null) {
            return "text/html;charset=utf-8";
        }
        return headers.getOrDefault("Accept", "")
                .split(",")[0];
    }

    public String getRequestBody() {
        if (headers == null) {
            return "";
        }
        return requestBody;
    }
}
