package webserver.http;

import java.util.Map;

public class RequestLine {

    private static final String SEPARATOR = " ";
    private static final String QUERY_PREFIX = "\\?";

    private String method;
    private String path;
    private RequestParameter parameter;

    private RequestLine(String method, String path, RequestParameter parameter) {
        this.method = method;
        this.path = path;
        this.parameter = parameter;
    }

    public static RequestLine parse(String rawRequestLine) {
        String[] requestLine = rawRequestLine.split(SEPARATOR);
        String[] requestUri = requestLine[1].split(QUERY_PREFIX);
        return new RequestLine(requestLine[0], requestUri[0], parseQueryString(requestUri));
    }

    private static RequestParameter parseQueryString(String[] requestUri) {
        if (requestUri.length == 1) {
            return RequestParameter.EMPTY;
        }
        return RequestParameter.parse(requestUri[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameter.getParameters();
    }
}
