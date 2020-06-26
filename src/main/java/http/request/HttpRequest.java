package http.request;

import com.google.common.collect.Maps;
import http.HttpCookies;
import http.HttpMethod;
import http.HttpSession;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestParams requestParams;
    private final HttpRequestHeaders httpRequestHeaders;
    private final Map<String, Object> attributes = Maps.newHashMap();

    private HttpRequest(final RequestLine requestLine,
                        final RequestParams requestParams,
                        final HttpRequestHeaders httpRequestHeaders) {
        this.requestLine = requestLine;
        this.requestParams = requestParams;
        this.httpRequestHeaders = httpRequestHeaders;
    }

    public static HttpRequest from(final InputStream in) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        final RequestLine requestLine = RequestLine.of(getRequestLine(br));
        final RequestParams requestParams = RequestParams.from(requestLine.getQueryString());
        final HttpRequestHeaders httpRequestHeaders = getHttpHeaders(br);
        requestParams.addParams(IOUtils.readData(br, httpRequestHeaders.getContentLength()));
        return new HttpRequest(requestLine, requestParams, httpRequestHeaders);
    }

    private static String getRequestLine(final BufferedReader br) throws IOException {
        final String line = br.readLine();
        if (line == null) {
            throw new IllegalArgumentException("HttpRequest must have RequestLine");
        }
        return line;
    }

    private static HttpRequestHeaders getHttpHeaders(final BufferedReader br) throws IOException {
        final HttpRequestHeaders httpRequestHeaders = new HttpRequestHeaders();
        String line;
        while ((line = br.readLine()) != null && !line.equals("")) {
            httpRequestHeaders.addHeader(line);
        }
        return httpRequestHeaders;
    }

    public Map<String, Object> getAttribute() {
        return attributes;
    }

    public void addAttribute(final String key, final Object value) {
        attributes.put(key, value);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getParameter(final String key) {
        return requestParams.getParameter(key);
    }

    public String getHeader(final String key) {
        return httpRequestHeaders.getHeader(key);
    }

    public HttpCookies getCookies() {
        return httpRequestHeaders.getCookies();
    }

    public HttpSession getSession() {
        return httpRequestHeaders.getSession();
    }
}
