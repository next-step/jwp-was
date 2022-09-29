package webserver.http.request;

import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;

import java.util.Arrays;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
    }

    public boolean isSignIn() {
        if (httpHeaders.hasCookie()) {
            String cookieValue = httpHeaders.getHeaders().get(HttpHeader.COOKIE);
            String[] cookies = cookieValue.split(";");
            return Arrays.stream(cookies)
                    .filter(e -> e.contains("logined"))
                    .map(logined -> logined.split("="))
                    .map(e -> e[1].trim())
                    .anyMatch(result -> result.equals("true"));
        }
        return false;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestBody getBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                '}';
    }
}
