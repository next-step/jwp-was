package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static utils.IOUtils.readData;
import static utils.IOUtils.readLines;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    public static final String ROOT_PATH = "/";
    public static final String ROOT_FILE = "/index.html";

    private final RequestLine requestLine;
    private final RequestHeader header;
    private final RequestBody requestBody;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        List<String> requests = readLines(br);
        this.requestLine = new RequestLine(requests.get(0));
        requests.remove(0);
        this.header = new RequestHeader(requests);
        this.requestBody = new RequestBody(readData(br, this.header.getContentLength()));
    }

    public HttpRequest(RequestLine requestLine, RequestHeader header, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.header = header;
        this.requestBody = requestBody;
    }

    public String getRequestPath() {
        return StringUtils.equals(requestLine.getPathWithoutQueryString(), ROOT_PATH) ? getRedirectUrl() : requestLine.getPathWithoutQueryString();
    }

    public Map<String, String> getRequestQueryString() {
        return requestLine.getQueryStringWithoutPathFromPath();
    }

    public String getMethod() {
        return requestLine.getHttpMethod().name();
    }

    public String getPath() {
        return requestLine.getPath().getPath();
    }

    private String getRedirectUrl() {
        return ROOT_FILE;
    }

    public RequestHeader getHeaders() {
        return header;
    }

    public String getHeader(String key) {
        return header.getHeaders().get(key);
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest httpRequest = (HttpRequest) o;
        return Objects.equals(requestLine, httpRequest.requestLine) && Objects.equals(header, httpRequest.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, header);
    }

}
