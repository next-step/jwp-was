package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final String HEADER_COOKIE_KEY = "Cookie";
    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private Cookie cookie;
    private RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
        this.cookie = Cookie.parse(httpHeaders.get(HEADER_COOKIE_KEY));
    }

    public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.cookie = Cookie.parse(httpHeaders.get(HEADER_COOKIE_KEY));
    }

    public static HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        HttpHeaders httpHeaders = HttpHeaders.parse(bufferedReader);

        if (HttpMethod.POST.equals(requestLine.getMethod())) {
            RequestBody requestBody = RequestBody.parse(bufferedReader, httpHeaders);
            return new HttpRequest(requestLine, httpHeaders, requestBody);
        }

        return new HttpRequest(requestLine, httpHeaders);
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public URI getUri() {
        return this.requestLine.getUri();
    }

    public String getHeaderValue(String key) {
        return this.httpHeaders.get(key);
    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }

    public RequestBody getRequestBody() {
        return this.requestBody;
    }

    public Cookie getCookie() {
        return this.cookie;
    }
}
