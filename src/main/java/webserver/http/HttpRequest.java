package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
    }

    public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
    }

    public static HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        HttpHeaders httpHeaders = HttpHeaders.parse(bufferedReader);

        if ("POST".equals(requestLine.getMethod())) {
            RequestBody requestBody = RequestBody.parse(bufferedReader, httpHeaders);
            return new HttpRequest(requestLine, httpHeaders, requestBody);
        }

        return new HttpRequest(requestLine, httpHeaders);
    }

    public String getMethod() {
        return this.requestLine.getMethod();
    }

    public URI getUri() {
        return this.requestLine.getUri();
    }

    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }

    public RequestBody getRequestBody() {
        return this.requestBody;
    }
}
