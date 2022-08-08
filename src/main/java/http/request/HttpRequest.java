package http.request;

import utils.IOUtils;
import http.HttpHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class HttpRequest {

    private final static int REQUEST_LINE_INDEX = 0;

    private RequestLine requestLine;
    private HttpHeader requestHeader;
    private RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, HttpHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = HttpRequest.parseRequest(IOUtils.readLines(br));
        if (httpRequest.getContentLength() > 0) {
            return httpRequest.withBody(RequestBody.from(IOUtils.readData(br, httpRequest.getContentLength())));
        }
        return httpRequest;
    }

    public static HttpRequest of(HttpRequest request, RequestBody body) {
        return new HttpRequest(request.requestLine, request.requestHeader, body);
    }

    public static HttpRequest parseRequest(List<String> request) {
        return new HttpRequest(
                new RequestLine(request.get(REQUEST_LINE_INDEX)),
                HttpHeader.from(request.subList(REQUEST_LINE_INDEX, request.size())),
                RequestBody.empty()
        );
    }

    private HttpRequest withBody(RequestBody body) {
        return of(this, body);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getRequestHeader(String key) {
        return requestHeader.get(key);
    }

    public int getContentLength() {
        return requestHeader.getContentLength();
    }

    public String getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public String getParameter(String key) {
        return requestLine.getParameter(key);
    }

    public Optional<String> getBody(String key) {
        return requestBody.get(key);
    }

    public String getCookieValue(String key) {
        return requestHeader.getCookieValue(key);
    }
}
