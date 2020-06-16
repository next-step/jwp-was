package http.request;

import com.google.common.collect.Maps;
import http.HttpCookies;
import http.HttpMethod;
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
    private final HttpReqeustHeaders httpReqeustHeaders;
    private final Map<String, Object> attributes = Maps.newHashMap();

    private HttpRequest(final RequestLine requestLine,
                        final RequestParams requestParams,
                        final HttpReqeustHeaders httpReqeustHeaders) {
        this.requestLine = requestLine;
        this.requestParams = requestParams;
        this.httpReqeustHeaders = httpReqeustHeaders;
    }

    public static HttpRequest from(final InputStream in) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        final RequestLine requestLine = RequestLine.of(getRequestLine(br));
        final RequestParams requestParams = new RequestParams();
        requestParams.addParams(requestLine.getQueryString());
        final HttpReqeustHeaders httpReqeustHeaders = getHttpHeaders(br);
        requestParams.addParams(IOUtils.readData(br, httpReqeustHeaders.getContentLength()));
        return new HttpRequest(requestLine, requestParams, httpReqeustHeaders);
    }

    private static String getRequestLine(final BufferedReader br) throws IOException {
        final String line = br.readLine();
        if (line == null) {
            throw new IllegalArgumentException("HttpRequest must have RequestLine");
        }
        return line;
    }

    private static HttpReqeustHeaders getHttpHeaders(final BufferedReader br) throws IOException {
        final HttpReqeustHeaders httpReqeustHeaders = new HttpReqeustHeaders();
        String line;
        while ((line = br.readLine()) != null && !line.equals("")) {
            httpReqeustHeaders.addHeader(line);
        }
        return httpReqeustHeaders;
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
        return httpReqeustHeaders.getHeader(key);
    }

    public HttpCookies getCookies() {
        return httpReqeustHeaders.getCookies();
    }
}
