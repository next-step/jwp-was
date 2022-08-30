package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import webserver.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static utils.IOUtils.readData;
import static utils.IOUtils.readLines;

public class HttpRequest {
    public static final String ROOT_PATH = "/";
    public static final String ROOT_FILE = "/index.html";

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeader header, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = header;
        this.requestBody = requestBody;
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        initializedRequestLine(br);
        initializedRequestHeader(br);
        initializedRequestBody(br);
    }

    private void initializedRequestBody(BufferedReader br) throws IOException {
        this.requestBody = new RequestBody(readData(br, this.requestHeader.getContentLength()));
    }

    private void initializedRequestHeader(BufferedReader br) throws IOException {
        this.requestHeader = new RequestHeader(readLines(br));
    }

    private void initializedRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            return;
        }
        this.requestLine = new RequestLine(line);
    }

    public String getMethod() {
        return requestLine.getHttpMethod().name();
    }

    public String getPath() {
        return requestLine.getPath().getPath();
    }

    public RequestHeader getHeaders() {
        return requestHeader;
    }

    public String getHeader(String key) {
        return (String) requestHeader.getHeaders().get(key);
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public String getRequestPath() {
        return StringUtils.equals(requestLine.getPathWithoutQueryString(), ROOT_PATH) ? getRedirectUrl() : requestLine.getPathWithoutQueryString();
    }

    public boolean isPost() {
        return getHttpMethod() == HttpMethod.POST;
    }

    public boolean isGet() {
        return getHttpMethod() == HttpMethod.GET;
    }

    public String getParameter(String key) {
        String value = requestLine.getQueryStringWithoutPathFromPath().get(key);
        if (value == null) {
            value = requestBody.getRequestBodyMap().get(key);
        }
        return value;
    }

    private String getRedirectUrl() {
        return ROOT_FILE;
    }

    public String getSessionId() {
        return this.requestHeader.getSessionId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine) && Objects.equals(requestHeader, that.requestHeader) && Objects.equals(requestBody, that.requestBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, requestHeader, requestBody);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", requestHeader=" + requestHeader +
                ", requestBody=" + requestBody +
                '}';
    }
}
