package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private RequestLine requestLine;
    private HttpHeaders httpHeaders;

    private HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
    }

    public static HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        HttpHeaders httpHeaders = HttpHeaders.parse(bufferedReader);

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
        return requestLine;
    }
}
