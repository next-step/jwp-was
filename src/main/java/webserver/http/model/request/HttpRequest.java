package webserver.http.model.request;

import webserver.http.model.session.HttpSession;
import webserver.http.model.session.HttpSessions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static constant.GlobalConstant.COOKIE;
import static constant.GlobalConstant.JSSESSION_ID;

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
        String value = null;
        if (requestBody != null) {
            value = this.requestBody.getRequestBodyMap().get(parameter);
        }

        if (requestLine.getQueryString() != null && value == null) {
            return requestLine.getQueryStringMap().get(parameter);
        }
        return value;
    }

    public HttpSession getHttpSession(String sessionId) {
        String cookie = getHeader(COOKIE);
        if (cookie == null) {
            return null;
        }

        String[] splitCookie = cookie.split("=");
        if (splitCookie.length < 2 || !splitCookie[0].equals(sessionId)) {
            return null;
        }
        return HttpSessions.getHttpSessionMap().get(splitCookie[1]);
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