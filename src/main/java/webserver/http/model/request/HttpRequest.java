package webserver.http.model.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = bufferedReader.readLine();
        this.requestLine = new RequestLine(line);
        this.requestHeaders = new RequestHeaders(bufferedReader, line);
        this.requestBody = null;
        if (HttpMethod.isPost(requestLine.getMethod())) {
            this.requestBody = new RequestBody(bufferedReader, requestHeaders);
        }
    }

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        this.requestLine = new RequestLine(line);
        this.requestHeaders = new RequestHeaders(bufferedReader, line);
        this.requestBody = null;
        if (HttpMethod.isPost(requestLine.getMethod())) {
            this.requestBody = new RequestBody(bufferedReader, requestHeaders);
        }
    }

    public HttpRequest(String httpRequestText) {
        HttpRequestLines httpRequestLines = new HttpRequestLines(httpRequestText);
        this.requestLine = new RequestLine(httpRequestLines.requestLine());
        this.requestHeaders = new RequestHeaders(httpRequestLines.requestHeader());
        this.requestBody = RequestBody.empty();
        if (HttpMethod.isPost(requestLine.getMethod())) {
            this.requestBody = new RequestBody(httpRequestLines.requestBody());
        }
    }

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.path();
    }

    public String getHeader(String header) {
        return requestHeaders.getRequestHeadersMap().get(header);
    }

    public String getParameter(String parameter) {
        if (requestBody != null) {
            return this.requestBody.getRequestBodyMap().get(parameter);
        }

        if (requestLine.getQueryString() != null) {
            return requestLine.getQueryString().getQueryStringMap().get(parameter);
        }
        return null;
    }

    public boolean isStaticResource() {
        return requestLine.isStaticResource();
    }

    public String responsePath() {
        return requestLine.fullPath();
    }

    public QueryString getQueryString() {
        return requestLine.getQueryString();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders.getRequestHeadersMap();
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}