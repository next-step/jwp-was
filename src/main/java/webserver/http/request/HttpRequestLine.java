package webserver.http.request;

import utils.HttpStringType;
import utils.HttpStringUtils;

import java.util.Optional;

public class HttpRequestLine {
    private String method;
    private String requestUri;
    private String queryString;

    private HttpRequestLine(String method, String requestUri) {
        this.method = method;
        this.requestUri = requestUri;
    }

    private HttpRequestLine(String method, String[] requestPath) {
        this.method = method;
        this.requestUri = requestPath[0];
        this.queryString = requestPath[1];
    }

    public static HttpRequestLine of(String requestLine) {
        if (requestLine == null) {
            return defaultRequestLine();
        }

        String[] requestLineValues = HttpStringUtils.split(requestLine, HttpStringType.DLM_SPACE.getType());

        if (isQueryString(requestLineValues)) {
            return new HttpRequestLine(requestLineValues[0],
                    HttpStringUtils.split(requestLineValues[1], HttpStringType.DLM_QUESTION.getType()));
        }

        return new HttpRequestLine(requestLineValues[0], requestLineValues[1]);
    }

    private static HttpRequestLine defaultRequestLine() {
        return new HttpRequestLine("GET", "/");
    }

    private static boolean isQueryString(String[] requestLineValues) {
        if ("POST".equals(requestLineValues[0])) {
            return false;
        }

        return HttpStringUtils.isPatternMatch(HttpStringType.QUERYSTRING_PATTERN.getType(), requestLineValues[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getQueryString() {
        return Optional.ofNullable(queryString)
                .orElse("noQueryString");
    }

    public boolean withQueryString() {
        return "GET".equals(method) && queryString != null;
    }
}
