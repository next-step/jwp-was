package webserver.http;

import utils.IOUtils;
import webserver.http.request.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private final String LOGIN_KEY = "logined";

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private Map<String, String> parameters;
    private Map<String, Cookie> cookies;

    public HttpRequest(InputStream in) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();

        requestLine = RequestLine.parse(line);
        httpHeaders = readHeader(br);
        parameters = createParameters(br, requestLine, httpHeaders);
        cookies = HttpUtils.parserCookie(httpHeaders.get("Cookie"));
    }

    private HttpHeaders readHeader(BufferedReader br) throws IOException {
        String line;
        HttpHeaders httpHeaders = new HttpHeaders();
        while (!"".equals(line = br.readLine())) {
            if (null == line) break;
            httpHeaders.add(line);
        }
        return httpHeaders;
    }

    private Map<String, String> createParameters(BufferedReader br, RequestLine requestLine, HttpHeaders httpHeaders) throws IOException {
        if (HttpMethod.isPOST(getMethod())) {
            int contentLength = Integer.valueOf(httpHeaders.get("Content-Length"));
            if (contentLength > 0) {
                return HttpUtils.parseQueryString(IOUtils.readData(br, contentLength));
            }
        }
        return HttpUtils.parseQueryString(requestLine.getQueryString());
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String key) { return httpHeaders.get(key); }

    public String getQueryString() {
        return requestLine.getQueryString();
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Cookie getCookie(String name) {
        return cookies.get(name);
    }

    public boolean isLogined() {
        return Optional.ofNullable(cookies.get(LOGIN_KEY))
                .map(cookie -> Boolean.parseBoolean(cookie.getValue()))
                .orElse(false);
    }
}
