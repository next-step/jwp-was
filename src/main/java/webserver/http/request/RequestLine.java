package webserver.http.request;

import webserver.http.request.exception.HttpMethodNotSupportedException;

/**
 * @author : yusik
 * @date : 2019-08-01
 */
public class RequestLine {

    private static final String LINE_SEPARATOR = " ";
    private HttpMethod method;
    private RequestURI requestURI;
    private String httpVersion;

    public RequestLine(HttpMethod method, RequestURI requestURI, String httpVersion) {
        this.method = method;
        this.requestURI = requestURI;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String requestLine) {
        String[] parsedLines = requestLine.split(LINE_SEPARATOR);
        if (!HttpMethod.contains(parsedLines[0])) {
            throw new HttpMethodNotSupportedException();
        }
        return new RequestLine(HttpMethod.valueOf(parsedLines[0]), RequestURI.parse(parsedLines[1]), parsedLines[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public RequestURI getRequestURI() {
        return requestURI;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
