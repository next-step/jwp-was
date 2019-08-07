package webserver.http;

import webserver.http.request.HttpMethod;

public class RequestLine {
    private final String version;
    private final HttpMethod method;
    private final RequestURL requestURL;

    public RequestLine(HttpMethod method, RequestURL requestURL, String version) {
        this.method = method;
        this.requestURL = requestURL;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return requestURL.getPath();
    }

    public String getQueryString() {
        return requestURL.getQueryString();
    }

    public static RequestLine parse(String requestLine) {
        String[] splitedRequestLine = requestLine.split(" ");
        HttpMethod method = HttpMethod.lookup(splitedRequestLine[0]);
        String path = splitedRequestLine[1];
        String version = splitedRequestLine[2];
        return new RequestLine(method, RequestURL.parse(path), version);
    }
}
