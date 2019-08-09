package webserver.http.request;

import utils.IOUtils;
import webserver.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements Request {

    private static final String ACCEPT_SEPARATOR = ",";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String ACCEPT_KEY = "Accept";
    private static final String COOKIE_KEY = "Cookie";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String COLON_SEPARATOR = ": ";
    private static final String COOKIE_SEPARATOR = ";";

    private RequestLine requestLine;
    private Map<String, String> requestHeaders;
    private Map<String, String> cookies;
    private QueryParam body = QueryParam.EMPTY_QUERY_PARAM;

    public HttpRequest(InputStream in) throws IOException {
        requestHeaders = new HashMap<>();
        cookies = new HashMap<>();
        process(in);
    }

    @Override
    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    @Override
    public String getParameter(String key) {
        return requestLine.getParameter(key);
    }

    @Override
    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
    }

    @Override
    public void process(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String line = br.readLine();
        requestLine = RequestLine.parse(line);
        System.out.println(line);
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] keyValue = line.split(COLON_SEPARATOR);
            requestHeaders.put(keyValue[0], keyValue[1]);
        }

        String cookieStr = requestHeaders.get(COOKIE_KEY);
        if (cookieStr != null) {
            Arrays.stream(cookieStr.split(COOKIE_SEPARATOR))
                    .map(String::trim)
                    .map(cookie -> cookie.split(KEY_VALUE_SEPARATOR))
                    .forEach(keyValue -> cookies.put(keyValue[0], keyValue[1]));
        }

        String contentLength = requestHeaders.get(CONTENT_LENGTH_KEY);
        if (contentLength != null) {
            body = QueryParam.parse(RequestBodyToString(br, contentLength));
        }
    }

    private String RequestBodyToString(BufferedReader br, String contentLength) throws IOException {
        return IOUtils.readData(br, Integer.parseInt(contentLength));
    }

    public boolean isGetRequest() {
        return requestLine.getHttpMethod() == HttpMethod.GET;
    }

    public boolean isPostRequest() {
        return requestLine.getHttpMethod() == HttpMethod.POST;
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public String getBodyParameter(String key) {
        return body.getParameter(key);
    }

    public String getAccept() {
        return requestHeaders.get(ACCEPT_KEY)
                .split(ACCEPT_SEPARATOR)[0];
    }
}
