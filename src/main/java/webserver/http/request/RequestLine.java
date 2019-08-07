package webserver.http.request;

import webserver.http.HttpMethod;

import java.util.Map;

public class RequestLine {

    private static final String REQUEST_LINE_SEPARATOR = " ";

    private HttpMethod httpMethod;
    private RequestUri requestUri;
    private String httpVersion;

    private RequestLine(HttpMethod httpMethod, RequestUri requestUri, String httpVersion) {
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String line) {
        String[] values = line.split(REQUEST_LINE_SEPARATOR);

        RequestUri requestUri = RequestUri.parse(values[1]);
        HttpMethod httpMethod = HttpMethod.resolve(values[0]);

        return new RequestLine(httpMethod, requestUri, values[2]);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return requestUri.getPath();
    }

    public Map<String, String> getQueryParams() {
        return requestUri.getQueryParams();
    }

    public String getParameter(String key) {
        return requestUri.getParameter(key);
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
