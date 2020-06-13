package http.request;

import com.google.common.collect.Maps;
import http.common.Cookies;
import http.common.HttpEntity;
import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.request.requestline.Protocol;
import http.request.requestline.RequestLine;
import lombok.extern.slf4j.Slf4j;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static http.common.HttpHeader.CONTENT_LENGTH_NAME;
import static http.common.HttpHeaders.COOKIE_HEADER_NAME;

@Slf4j
public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpEntity httpEntity;
    private final Cookies cookies;

    public HttpRequest(RequestLine requestLine, HttpEntity httpEntity) {
        this.requestLine = requestLine;
        this.httpEntity = httpEntity;
        this.cookies = Cookies.parse(getHeaderValue(COOKIE_HEADER_NAME));
    }

    public static HttpRequest parse(BufferedReader br) throws IOException {
        String request = br.readLine();

        if (Objects.isNull(request)) {
            return null;
        }

        RequestLine requestLine = RequestLine.parse(request);
        HttpHeaders httpHeaders = HttpHeaders.parse(br);

        if (HttpMethod.GET.equals(requestLine.getMethod())) {
            return HttpRequest.fromGet(requestLine, httpHeaders);
        }

        String httpBody = getHttpBody(br, StringUtils.toInt(httpHeaders.getHeaderValue(CONTENT_LENGTH_NAME)));
        return HttpRequest.fromPost(requestLine, httpHeaders, httpBody);
    }

    private static String getHttpBody(BufferedReader br, int contentLength) throws IOException {
        char[] buffer = new char[contentLength];
        br.read(buffer, 0, contentLength);

        String httpBody = new String(buffer, 0, buffer.length);
        return httpBody;
    }

    private static HttpRequest fromGet(RequestLine requestLine, HttpHeaders httpHeaders) {
        return new HttpRequest(requestLine, new HttpEntity(httpHeaders));
    }

    private static HttpRequest fromPost(RequestLine requestLine, HttpHeaders httpHeaders, String httpBody) {
        return new HttpRequest(requestLine, new HttpEntity(httpHeaders, httpBody));
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public Protocol getProtocol() {
        return requestLine.getProtocol();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getParameterMap() {
        return Optional.ofNullable(httpEntity)
            .map(HttpEntity::getBodyMap)
            .map(this::getMergedBodyAndQueryStrings)
            .orElse(requestLine.getQueryString());
    }

    private Map<String, String> getMergedBodyAndQueryStrings(Map<String, String> bodyMap) {
        Map<String, String> parameterMap = Maps.newHashMap(bodyMap);
        parameterMap.putAll(requestLine.getQueryString());
        return parameterMap;
    }

    public String getParameterMapValue(String key) {
        return getParameterMap().get(key);
    }

    public String getHeaderValue(String key) {
        return httpEntity.getHeaderValue(key);
    }

    public String getCookie(String key) {
        return cookies.getCookie(key);
    }
}
