package webserver.http;

import webserver.http.exception.HttpMethodNotSupportedException;

/**
 * @author : yusik
 * @date : 2019-08-01
 */
public class RequestLine {

    private static final String LINE_SEPARATOR = " ";
    private HttpMethod method;
    private String path;

    public RequestLine(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestLine parse(String requestLine) {
        String[] parsedLines = requestLine.split(LINE_SEPARATOR);
        if (!HttpMethod.contains(parsedLines[0])) {
            throw new HttpMethodNotSupportedException();
        }
        return new RequestLine(HttpMethod.valueOf(parsedLines[0]), parsedLines[1]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
