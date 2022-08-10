package webserver.http.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        this.requestLine = new RequestLine(line);
        this.requestHeaders = new RequestHeaders(bufferedReader, line);
        this.requestBody = null;
        if (Method.isPost(requestLine.getMethod())) {
            this.requestBody = new RequestBody(bufferedReader, requestHeaders);
        }
    }

    public HttpRequest(String httpRequestText) {
        HttpRequestLines httpRequestLines = new HttpRequestLines(httpRequestText);
        this.requestLine = new RequestLine(httpRequestLines.requestLine());
        this.requestHeaders = new RequestHeaders(httpRequestLines.requestHeader());
        this.requestBody = RequestBody.empty();
        if (Method.isPost(requestLine.getMethod())) {
            this.requestBody = new RequestBody(httpRequestLines.requestBody());
        }
    }

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public boolean isStaticResource() {
        return requestLine.isStaticResource();
    }

    public String responsePath() {
        return requestLine.fullPath();
    }

    public String path() {
        return requestLine.path();
    }

    public QueryStrings getQueryStrings() {
        return requestLine.getQueryStrings();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders.getRequestHeadersMap();
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}